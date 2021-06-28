<template>
    <div style="overflow: hidden">
        <!--表头搜索，添加区域-->
        <div class="search_content">
            <div>
                <el-input v-model="searchObj.name"
                          @clear="loadTableData"
                          @keydown.enter.native="loadTableData"
                          clearable
                          style="width: 300px;margin-right: 10px"
                          placeholder="输入工资账套名称进行查找"
                          size="small" />
                <el-button @click="loadTableData"
                           type="primary"
                           icon="el-icon-search"
                           class="change_btn"
                           size="small">搜索</el-button>
            </div>
            <div>
                <el-button @click="handleAdd"
                           type="primary"
                           icon="el-icon-plus"
                           size="small">添加</el-button>
            </div>
        </div>
        <!--数据表格区域-->
        <div>
            <el-table :data="tableData"
                      stripe
                      size="small"
                      v-loading="loading"
                      element-loading-text="加载中..."
                      element-loading-spinner="fa fa-cog fa-spin"
                      style="width: 100%;margin-top: 20px">
                <el-table-column prop="id"
                                 label="id"
                                 width="50"
                                 align="center">
                </el-table-column>
                <el-table-column prop="name"
                                 label="名称"
                                 align="center">
                </el-table-column>
                <el-table-column prop="basicSalary"
                                 label="基本工资"
                                 align="center">
                </el-table-column>
                <el-table-column prop="bonus"
                                 label="奖金"
                                 align="center">
                </el-table-column>
                <el-table-column prop="lunchSalary"
                                 label="午餐补助"
                                 align="center">
                </el-table-column>
                <el-table-column prop="trafficSalary"
                                 label="交通补助"
                                 align="center">
                </el-table-column>
                <el-table-column label="养老金"
                                 align="center">
                    <el-table-column prop="pensionBase"
                                     label="基数"
                                     align="center">
                    </el-table-column>
                    <el-table-column prop="pensionPer"
                                     label="比率"
                                     align="center">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="医疗保险"
                                 align="center">
                    <el-table-column prop="medicalBase"
                                     label="基数"
                                     align="center">
                    </el-table-column>
                    <el-table-column prop="medicalPer"
                                     label="比率"
                                     align="center">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="公积金"
                                 align="center">
                    <el-table-column prop="accumulationFundBase"
                                     label="基数"
                                     align="center">
                    </el-table-column>
                    <el-table-column prop="accumulationFundPer"
                                     label="比率"
                                     align="center">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="allSalary"
                                 label="应发工资"
                                 align="center">
                </el-table-column>
                <el-table-column prop="createDate"
                                 label="启用时间"
                                 align="center">
                </el-table-column>
                <el-table-column label="操作"
                                 align="center"
                                 width="130px">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   icon="el-icon-edit"
                                   style="background-color: #13ce66;color: #FFF"
                                   @click="handleEdit(scope.$index, scope.row)"></el-button>
                        <el-button size="mini"
                                   type="danger"
                                   icon="el-icon-delete"
                                   style="background-color: #ff4949"
                                   @click="handleDelete(scope.$index, scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination style="margin-top: 25px"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :page-sizes="[6, 12, 18, 24, 30, 60]"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="pageObj.total">
            </el-pagination>
        </div>

        <!--对话框-->
        <el-dialog :title="optionStatus==='add'?'添加':'编辑'"
                   :visible.sync="dialogVisible"
                   width="85%"
                   top="10%">
            <!--主体-->
            <div>
                <el-steps :active="stepActive"
                          finish-status="success"
                          size="small">
                    <el-step :title="item"
                             v-for="(item,i) in dialogSalaryStepItem"
                             :key="i"></el-step>
                </el-steps>
                <div style="width: 400px;margin: 40px auto 0">
                    <el-input v-show="stepActive==i"
                              v-model="optionObj[title]"
                              :placeholder="'请输入'+ dialogSalaryStepItem[i]"
                              v-for="(value,title,i) in optionObj"
                              @keydown.enter.native="stepActive++"
                              :key="i"
                              clearable
                              size="small" />
                </div>

            </div>
            <span slot="footer"
                  class="dialog-footer">

                <el-button @click="dialogVisible = false"
                           size="small">取 消</el-button>
                <el-button :disabled="stepActive <= 0"
                           @click="stepActive--"
                           type="primary"
                           size="small">上 一 步</el-button>
                <el-button :disabled="stepActive>dialogSalaryStepItem.length-2"
                           type="primary"
                           @click="stepActive++"
                           size="small">下 一 步</el-button>
                <el-button :disabled="stepActive<dialogSalaryStepItem.length-1"
                           type="primary"
                           @click="dialogSubmit"
                           size="small">确 定</el-button>

            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "SalSob",
    data () {
        return {
            //表格加载
            loading: false,
            //当前操作状态
            optionStatus: '',
            //步骤条名称
            dialogSalaryStepItem: ['名称', '基本工资', '奖金', '午餐补助', '交通补助', '养老基金数', '养老金比率', '医疗基数', '医疗保险比率', '公积金基数', '公积金比率'],
            //弹出层显隐
            dialogVisible: false,
            //步骤条成功的下标
            stepActive: 0,
            //表格数据
            tableData: [],
            //搜索对象
            searchObj: {
                name: ''
            },
            //操作对象
            optionObj: {},
            //分页对象
            pageObj: {
                //当前第几页
                page: 1,
                //每页显示多少条
                size: 6,
                //总记录数
                total: 0,
                //输入框添加的值
            },
        }
    },
    mounted () {
        this.loadTableData();
    },
    methods: {
        // 加载表格数据方法
        loadTableData () {
            this.loading = true;
            this.getRequest('/salary/sob/?name=' + this.searchObj.name + '&page=' + this.pageObj.page + '&size=' + this.pageObj.size).then(resp => {
                if (resp) {
                    this.loading = false;
                    this.tableData = resp.data;
                    this.pageObj.total = resp.count;
                }
            })
        },
        // 添加按钮点击事件
        handleAdd () {
            // 重置步骤
            this.stepActive = 0;
            // 设置操作状态为添加
            this.optionStatus = 'add';
            // 显示对话框
            this.dialogVisible = true;
            // 设置属性
            this.optionObj = {
                // 工资账套名称
                name: '',
                // 基本工资
                basicSalary: '',
                // 奖金
                bonus: '',
                // 餐补
                lunchSalary: '',
                // 交通补助
                trafficSalary: '',
                // 养老金基数
                pensionBase: '',
                // 养老金比例
                pensionPer: '',
                // 医疗保险基数
                medicalBase: '',
                // 医疗保险比例
                medicalPer: '',
                // 公积金基数
                accumulationFundBase: '',
                // 公积金比例
                accumulationFundPer: ''
            };
        },
        //编辑按钮点击事件
        handleEdit (index, data) {
            this.stepActive = 0;
            this.optionStatus = 'edit';
            this.dialogVisible = true;
            Object.assign(this.optionObj, data);
        },
        //删除按钮点击事件
        handleDelete (index, data) {
            this.$confirm('是否删除id为[' + data.id + ']的工资账套及对应的员工信息记录?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRequest('/salary/sob/' + data.id).then(resp => {
                    if (resp) {
                        //刷新表格
                        this.loadTableData();
                    }
                })
            })
        },
        //弹出层提交事件
        dialogSubmit () {
            if ('add' === this.optionStatus) {
                this.postRequest('/salary/sob/', this.optionObj).then(resp => {
                    if (resp) {
                        this.loadTableData();
                        this.dialogVisible = false;
                    }
                })
            } else if ('edit' === this.optionStatus) {
                this.putRequest('/salary/sob/', this.optionObj).then(resp => {
                    if (resp) {
                        this.loadTableData();
                        this.dialogVisible = false;
                    }
                })
            }
        },
        //分页处理
        handleSizeChange (size) {
            this.pageObj.size = size;
            this.loadTableData();
        },
        handleCurrentChange (currentPage) {
            this.pageObj.page = currentPage;
            this.loadTableData();
        },
    }
}
</script>

<style scoped>
/*表头搜索区域*/
.search_content {
    display: flex;
    justify-content: space-between;
    padding: 0 10px 0 10px;
    margin-top: 15px;
}
/*按钮位置微调*/
.change_btn {
    position: relative;
    top: -1px;
}
</style>
