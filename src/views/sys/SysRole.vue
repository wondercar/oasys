<template>
    <div style="height: 100%">
        <!--表头搜索区域-->
        <div>
            <el-button style="float: left"
                       @click="handleAdd"
                       icon="el-icon-document-add"
                       size="small"
                       type="primary">添加</el-button>
            <div style="display: flex;justify-content: flex-end">
                <el-input class="searchDataInput"
                          size="small"
                          placeholder="请输入角色名称"
                          prefix-icon="el-icon-user-solid"
                          @keydown.enter.native="handleSearch"
                          clearable
                          v-model="dataObj.name"
                          style="width: 300px">
                </el-input>
                <div size="small"
                     style="margin-left: 10px">
                    <el-input size="small"
                              @keydown.enter.native="handleSearch"
                              placeholder="请输入角色码名称"
                              v-model="dataObj.code"
                              clearable>
                        <template slot="prepend">ROLE_</template>
                    </el-input>
                </div>
                <el-button @click="handleSearch"
                           icon="el-icon-search"
                           size="small"
                           type="primary"
                           style="margin-left: 10px">搜索</el-button>
                <el-button @click="handleClear"
                           icon="el-icon-refresh-right"
                           size="small"
                           type="primary"
                           style="margin-left: 10px;position: relative;top: -1px">重置</el-button>
            </div>
        </div>
        <!--数据表头内容-->
        <div class="tableHead">
            <div>角色名称</div>
            <div>创建时间</div>
            <div>角色编码</div>
        </div>
        <!--中间主体区域-->
        <div v-loading="loading"
             element-loading-text="加载中..."
             element-loading-spinner="fa fa-cog fa-spin"
             class="role_content">

            <!--数据为0时展示-->
            <div v-if="pageObj.total <= 0"
                 style="text-align: center;font-size: 12px;color:#909399;padding-top:20px">
                暂无数据
            </div>
            <el-collapse v-else
                         @change="changeCollapse"
                         v-model="activeName"
                         accordion>
                <el-collapse-item :name="r.id"
                                  v-for="(r,i) in roles">
                    <template slot="title">
                        <div style="width: 33%">
                            <span :style="{'color': r.enabled ? '#13CE66' : '#FF4949'}">{{r.name}}</span>
                        </div>
                        <div style="width: 33%">
                            <span :style="{'color': r.enabled ? '#13CE66' : '#FF4949'}">{{r.gmtCreate}}</span>
                        </div>
                        <div style="width: 33%">
                            <el-tag size="small"
                                    :type="r.enabled?'success':'danger'"
                                    style="margin-left:20px">{{r.code}}</el-tag>
                        </div>

                    </template>
                    <el-card class="box-card">
                        <div slot="header"
                             class="clearfix">
                            <span>名称为{{r.name}}可访问的资源</span>
                            <div>
                                <el-button @click="closeCollapse"
                                           type="success"
                                           icon="el-icon-arrow-up"
                                           size="small"
                                           circle
                                           title="收起" />
                                <el-button @click="handleEdit(r)"
                                           type="primary"
                                           icon="el-icon-edit"
                                           size="small"
                                           circle
                                           title="编辑" />
                                <el-button @click="handleDelete(r)"
                                           type="danger"
                                           icon="el-icon-delete"
                                           size="small"
                                           circle
                                           title="删除" />
                            </div>
                        </div>
                        <div>
                            <!--菜单树形控件-->
                            <div v-if="treeMenuLoading"
                                 style="color: #808080;height:26px;background-color:#FFF;padding-left: 10px">
                                <i class="el-icon-loading" />&emsp;加载中...
                            </div>
                            <el-tree :indent="20"
                                     v-else
                                     highlight-current
                                     :data="allMenus"
                                     :props="treeProps"
                                     show-checkbox
                                     :key="i"
                                     node-key="id"
                                     :default-checked-keys="checkedMenus"
                                     ref="selectTree"
                                     style="border-radius: 5px">
                            </el-tree>
                            <div style="display: flex;justify-content: flex-end;margin-top:20px">
                                <el-button @click="closeCollapse"
                                           type="info"
                                           size="small">取消修改</el-button>
                                <el-button @click="updateCheckedMenus(r.id,i)"
                                           type="primary"
                                           size="small">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
        <el-pagination background
                       style="margin-top:15px"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :page-sizes="[8, 16, 32, 64]"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="pageObj.total">
        </el-pagination>

        <!--对话框区域-->
        <el-dialog :title="this.optionStatus === 'add' ? '添加' : '修改'"
                   :visible.sync="dialogVisible"
                   top="8%"
                   width="30%">
            <el-form label-width="90px"
                     ref="roleRef"
                     :rules="roleRules"
                     :model="optionObj">
                <el-form-item label="角色名称:"
                              prop="name">
                    <el-input v-model="optionObj.name"
                              placeholder="请输入角色名称" />
                </el-form-item>

                <el-form-item label="角色码:"
                              prop="code">
                    <el-input v-model="optionObj.code"
                              placeholder="默认以'ROLE_'为前缀" />
                </el-form-item>

                <el-form-item label="是否启用:">
                    <el-switch v-model="optionObj.enabled"
                               active-color="#13ce66"
                               inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>

                <el-form-item label="创建时间:">
                    <el-input v-model="optionObj.gmtCreate"
                              disabled />
                </el-form-item>

                <el-form-item label="角色备注">
                    <el-input type="textarea"
                              v-model="optionObj.remark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button size="small"
                           @click="dialogVisible = false">取 消</el-button>
                <el-button size="small"
                           type="primary"
                           @click="dialogSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "PermissMana",
    data () {
        return {
            loading: false,
            //操作状态
            optionStatus: '',
            //手风琴加载
            treeMenuLoading: true,
            //手风琴默认展开节点
            activeName: -1,
            //菜单集合
            allMenus: [],
            //选中的菜单集合
            checkedMenus: [],
            //手风琴绑定的属性
            treeProps: {
                label: 'name',
                children: 'children',
            },
            //分页对象
            pageObj: {
                //当前第几页
                page: 1,
                //每页显示多少条
                size: 8,
                //总记录数
                total: 0,
                //输入框添加的值
            },
            //搜索对象
            dataObj: {
                name: '',
                code: ''
            },
            //对话框添加或编辑的对象
            optionObj: {
                name: '',
                code: '',
                enabled: true,
                gmtCreate: '',
                remark: ''
            },
            //表格数据
            roles: [],
            //表单校验
            roleRules: {
                name: [{ required: true, message: '请输入角色名称', trigger: 'change' }],
                code: [{ required: true, message: '请输入角色编码', trigger: 'change' }]
            },
            //弹出层
            dialogVisible: false,
        }
    },
    //加载数据
    mounted () {
        this.loadCollapseData();
    },
    methods: {
        //加载数据
        loadCollapseData () {
            this.loading = true;
            this.getRequest('/system/basic/permission/?page=' + this.pageObj.page + '&size=' + this.pageObj.size + '&name=' + this.dataObj.name + '&code=' + this.dataObj.code).then(resp => {
                if (resp) {
                    this.roles = resp.data;
                    this.pageObj.total = resp.count;
                    this.loading = false;
                }
            })
        },
        //表头添加
        handleAdd () {
            //添加状态
            this.optionStatus = 'add';
            //显示对话框
            this.dialogVisible = true;
            //制空数据
            this.optionObj.enabled = true;
            this.optionObj.gmtCreate = '';
            this.optionObj.remark = '';
            this.$nextTick(() => {
                this.$refs['roleRef'].resetFields();
            });
        },
        //表头搜索
        handleSearch () {
            this.loadCollapseData();
        },
        //清空搜索条件
        handleClear () {
            this.dataObj.name = '';
            this.dataObj.code = '';
            this.loadCollapseData();
        },
        //折叠面板改变事件
        changeCollapse (id) {
            if (id) {
                this.loadMenus();
                this.loadCheckedMenus(id);
                this.treeMenuLoading = true;
            }
        },
        //收起全部手风琴
        closeCollapse () {
            this.activeName = -1;
        },
        //加载菜单
        loadMenus () {
            this.getRequest('/system/basic/permission/menus').then(resp => {
                if (resp) {
                    this.allMenus = resp;
                    this.treeMenuLoading = false;
                }
            })
        },
        //加载选中的节点
        loadCheckedMenus (id) {
            this.getRequest('/system/basic/permission/menuRole/' + id).then(resp => {
                if (resp) {
                    this.checkedMenus = resp;
                }
            })
        },
        //修改角色的菜单权限
        updateCheckedMenus (rid, index) {
            let selectTreeIndex = this.$refs.selectTree[index].getCheckedKeys(true);
            let mids = '';
            selectTreeIndex.forEach(m => {
                mids += '&mids=' + m;
            })
            this.putRequest('/system/basic/permission/menuRole?rid=' + rid + mids).then(resp => {
                if (resp) {
                    this.closeCollapse();
                }
            });
        },
        //修改角色
        handleEdit (role) {
            this.optionStatus = 'edit';
            //给对话框赋值(拷贝一份数据，防止双向绑定时，用户输入的值跟随表单中的值发生变化)
            Object.assign(this.optionObj, role);
            this.dialogVisible = true;
        },
        //对话框提交事件
        dialogSubmit () {
            this.$refs['roleRef'].validate(valid => {
                if (valid) {
                    if ('add' === this.optionStatus) {
                        //添加
                        this.postRequest('/system/basic/permission/', this.optionObj).then(resp => {
                            if (resp) {
                                this.loadCollapseData();
                                this.closeCollapse();
                                this.dialogVisible = false;
                            }
                        })
                    } else if ('edit' === this.optionStatus) {
                        //编辑
                        this.putRequest('/system/basic/permission/', this.optionObj).then(resp => {
                            if (resp) {
                                this.loadCollapseData();
                                this.closeCollapse();
                                this.dialogVisible = false;
                            }
                        })
                    }
                }
            })
        },
        //删除角色
        handleDelete (role) {
            this.$confirm('是否删除角色名称为 ' + role.name + ' 及该角色所属角色菜单表中的数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRequest('/system/basic/permission/' + role.id).then(resp => {
                    this.loadCollapseData();
                })
            })
        },
        //分页处理
        handleSizeChange (size) {
            this.pageObj.size = size;
            this.loadCollapseData();
        },
        handleCurrentChange (currentPage) {
            this.pageObj.page = currentPage;
            this.loadCollapseData();
        },
    }
}
</script>

<style scoped>
/*滚动条隐藏*/
::-webkit-scrollbar {
    display: none;
}
/*role主体部分*/
.role_content {
    overflow: auto;
    height: 400px;
}
/*数据表头*/
.tableHead {
    margin-top: 10px;
    width: 100%;
    display: flex;
    height: 48px;
    font-size: 12px;
    color: #909399;
    border-bottom: 1px solid #f5f5f5;
    line-height: 48px;
    font-weight: bold;
    justify-content: space-between;
}
.tableHead div {
    width: 33%;
}

/*手风琴展开的卡片头*/
.clearfix {
    padding: 0;
    display: flex;
    justify-content: space-between;
}
</style>
