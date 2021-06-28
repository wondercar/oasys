<template>
    <!--主容器-->
    <div class="deptContainer">
        <el-card class="box-card">
            <!--输入框-->
            <el-input size="small"
                      prefix-icon="el-icon-search"
                      placeholder="请输入名称自动查询"
                      v-model="filterText">
            </el-input>
            <!--提示-->
            <el-divider>
                <span style="font-size: 12px;color: grey"><span>{{isDrag ? dragTitle : '点击左侧箭头收缩节点(拖拽节点选择父节点)'}}</span></span>
            </el-divider>
            <!--树形控件-->
            <div style="height:440px;overflow:auto;">
                <div v-if="!treeLoad"
                     style="text-align: center;font-size: 60px;color: grey;margin-top: 20px"><i class="el-icon-loading" /></div>
                <div v-else>
                    <el-tree :expand-on-click-node="false"
                             highlight-current
                             default-expand-all
                             class="filter-tree"
                             :data="treeDate"
                             :props="defaultProps"
                             :draggable="true"
                             @node-click="nodeClick"
                             :filter-node-method="filterNode"
                             @node-drag-over="dragOver"
                             @node-drag-start="dragStart"
                             @node-drop="dragDrop"
                             @node-drag-end="dragEnd"
                             ref="tree">
                        <span class="custom-tree-node"
                              slot-scope="{ node, data }"
                              style="display: flex;justify-content: space-between;width: 100%">
                            <span :style="data.enabled ? '' : 'color:#FF4351'">{{ node.label }}</span>
                            <span>
                                <i @click="handleAdd(data)"
                                   class="el-icon-folder-add"
                                   style="color: #13ce66;margin-right: 20px"
                                   title="在该节点上添加子节点" />
                            </span>
                        </span>
                    </el-tree>
                </div>

            </div>
        </el-card>

        <el-card class="box-card">
            <el-card class="box-card"
                     shadow="never"
                     style="width: 100%">
                <div slot="header"
                     class="clearfix">
                    <i class="fa fa-info-circle"
                       style="margin-right: 5px" /><span>详细信息显示</span>
                    <el-popconfirm @confirm="handleDelete"
                                   :title="'确定要删除名称为[ '+ detailOptionObj.name +' ]的所有信息吗？'">
                        <el-button slot="reference"
                                   v-show="treeNodeClick"
                                   style="float: right; padding: 3px 0;margin-left: 30px;color: #FF4351"
                                   type="text"
                                   icon="el-icon-delete">删除</el-button>
                    </el-popconfirm>

                    <el-button @click="handleEdit"
                               v-show="treeNodeClick"
                               style="float: right; padding: 3px 0"
                               type="text"
                               icon="el-icon-edit">编辑</el-button>

                </div>
                <div v-if="!treeNodeClick"
                     class="detail_default_show_icon">
                    <i class="fa fa-clipboard fa-5x"
                       style="color: #CCC;font-size: 200px" />
                </div>
                <div v-else>
                    <ul class="deptDetailUl">
                        <li>部门id：<span>{{detailOptionObj.id}}</span></li>
                        <li>部门名称：<span>{{detailOptionObj.name}}</span></li>
                        <li>上级部门：<span>{{detailPName}}</span></li>
                        <li>是否启用：
                            <transition name="fade">
                                <el-tag v-if="detailOptionObj.enabled"
                                        type="success"
                                        style="color: #13CE66">启用</el-tag>
                                <el-tag v-else
                                        type="danger"
                                        style="color: #FF4949">禁用</el-tag>
                            </transition>
                        </li>
                        <li>创建时间：<span>{{detailOptionObj.createDate}}</span></li>
                        <li>部门路径：<span>{{detailOptionObj.depPath}}</span></li>
                        <li>部门描述：<span>{{detailOptionObj.remark}}</span></li>
                    </ul>
                </div>
            </el-card>
        </el-card>

        <!--对话框区域-->
        <el-dialog :title="optionStatus==='add' ? '添加部门' : '编辑部门'"
                   :visible.sync="dialogVisible"
                   top="10%"
                   width="30%">
            <el-form ref="editFormRef"
                     :rules="rules"
                     :model="optionObj"
                     label-width="80px"
                     size="small">
                <el-form-item label="上级部门">
                    <el-input v-model="pName"
                              disabled />
                </el-form-item>

                <el-form-item label="部门名称"
                              prop="name">
                    <el-input v-model="optionObj.name"
                              placeholder="请输入部门名称"
                              key="name" />
                </el-form-item>

                <el-form-item label="是否启用">
                    <el-switch v-model="optionObj.enabled"
                               size="small"
                               active-color="#13ce66"
                               inactive-color="#ff4949" />
                </el-form-item>

                <el-form-item label="部门路径">
                    <el-input v-model="optionObj.depPath"
                              placeholder="请输入部门路径" />
                </el-form-item>

                <el-form-item label="部门备注">
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
    name: "DepMana",
    data () {
        return {
            //搜索框文本
            filterText: '',
            treeDate: [],
            optionStatus: '',
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            dialogVisible: false,
            //操作的对象
            optionObj: {
                id: '',
                name: '',
                parentId: '',
                enabled: true,
                depPath: '',
                remark: ''
            },
            detailOptionObj: {
                name: '',
                parentId: '',
                enabled: true,
                createDate: '',
                depPath: '',
                remark: ''
            },
            treeNodeClick: false,
            //父节点名称
            pName: '',
            detailPName: '',
            //树形控件加载
            treeLoad: false,
            //拖拽状态提示文字
            dragTitle: '',
            //是否正在拖拽状态
            isDrag: false,
            rules: {
                name: [{ required: true, message: '请输入部门名称', trigger: 'change' }]
            }
        }
    },
    watch: {
        filterText (val) {
            this.$refs.tree.filter(val);
        }
    },
    mounted () {
        this.loadTreeDate();
    },
    methods: {
        //加载数据
        loadTreeDate () {
            this.treeLoad = false;
            this.getRequest('/system/basic/department/').then(resp => {
                if (resp) {
                    this.treeDate = resp;
                    this.treeLoad = true;
                }
            })
        },
        //树形控件原生方法
        filterNode (value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        //节点点击事件
        nodeClick (data, node, nodeComponent) {
            if (node.parent.data.name) {
                this.detailPName = node.parent.data.name;
            } else {
                this.detailPName = '根节点暂无父节点';
            }
            this.detailOptionObj = data;
            this.treeNodeClick = true;
        },
        //节点开始拖拽事件
        dragStart (currentNode, event) {
            this.isDrag = true;
        },
        //在拖拽节点时触发的事件
        dragOver (currentNode, targetNode, event) {
            this.dragTitle = '将[' + currentNode.data.name + ']及其子节点作为[' + targetNode.data.name + ']的子节点?(松开确定)';
        },
        dragEnd (currentNode, lastNode, location, event) {
            this.isDrag = false;
        },
        //拖拽成功完成时触发的事件
        dragDrop (currentNode, lastNode, location, event) {
            this.isDrag = false;
            //判断节点是否已经脱离出原节点
            this.putRequest('/system/basic/department/editParentId/?id=' + currentNode.data.id + '&parentId=' + lastNode.data.id).then(resp => {
                this.loadTreeDate();
            })
            /*this.$confirm('是否确定将[' + currentNode.data.name + ']及其子节点作为[' + lastNode.data.name + ']的子节点?', '请确定', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.putRequest('/system/basic/department/editParentId/?id=' + currentNode.data.id + '&parentId=' + lastNode.data.id).then(resp => {
                this.loadTreeDate();
              })
            });*/
        },
        //添加部门
        handleAdd (data) {
            this.dialogVisible = true;
            this.optionStatus = 'add';
            //制空数据
            this.optionObj.name = undefined;
            this.optionObj.enabled = true;
            this.optionObj.depPath = '';
            this.optionObj.remark = '';
            this.$nextTick(() => {
                this.$refs.editFormRef.resetFields();
            })
            //给pid赋值
            this.optionObj.parentId = data.id;
            this.pName = data.name;

        },
        //对话框提交事件
        dialogSubmit () {
            this.$refs['editFormRef'].validate(valid => {
                if (valid) {
                    //添加
                    if ('add' === this.optionStatus) {
                        this.postRequest('/system/basic/department/', this.optionObj).then(resp => {
                            if (resp) {
                                this.loadTreeDate();
                                this.dialogVisible = false;
                                window.sessionStorage.removeItem('departments');
                            }
                        })
                    } else if ('edit' === this.optionStatus) {
                        //编辑
                        this.putRequest('/system/basic/department/', this.optionObj).then(resp => {
                            if (resp) {
                                //修改完成后，刷新详情面板
                                this.detailOptionObj = resp.obj;
                                this.loadTreeDate();
                                this.dialogVisible = false;
                                window.sessionStorage.removeItem('departments');
                            }
                        })
                    }
                }
            });
        },
        //编辑部门
        handleEdit () {
            this.optionStatus = 'edit';
            this.dialogVisible = true;
            Object.assign(this.optionObj, this.detailOptionObj);
            this.pName = this.detailPName;
        },
        //删除部门
        handleDelete () {
            if (this.detailOptionObj.children <= 0) {
                this.deleteRequest('/system/basic/department/' + this.detailOptionObj.id).then(resp => {
                    if (resp) {
                        this.loadTreeDate();
                        this.treeNodeClick = false;
                        window.sessionStorage.removeItem('positions');
                    }
                })
            } else {
                this.$notify.error({
                    title: '错误',
                    message: '该部门下已有子部门，请先删除子部门！',
                });
            }
        }
    },
}
</script>

<style scoped>
/*主容器*/
.deptContainer {
    background-color: #f0f2f5;
    display: flex;
    justify-content: space-between;
}

/*页面加载完默认部门详情显示的icon*/
.detail_default_show_icon {
    text-align: center;
    padding-top: 100px;
}
.box-card {
    color: #606266;
    width: 48%;
    height: 540px;
    padding-right: 5px;
}
/*部门详细信息ul*/
.deptDetailUl {
    margin: 0;
    padding: 0;
    list-style: none;
}
.deptDetailUl li:nth-of-type(1) {
    margin-top: 10px;
}
.deptDetailUl li {
    margin-top: 40px;
}
.deptDetailUl li span {
    margin-left: 10px;
    font-size: 14px;
    color: #808080;
}
</style>
