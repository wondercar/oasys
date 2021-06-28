<template>
    <div>
        <!--头部搜索区域-->
        <div>
            <div>
                <el-input @keydown.enter.native="loadTableData"
                          @clear="loadTableData"
                          v-model="searchObj.workId"
                          clearable
                          style="width: 300px;margin-right: 10px"
                          placeholder="请输入员工工号进行查询"
                          size="small" />
                <el-input @keydown.enter.native="loadTableData"
                          @clear="loadTableData"
                          v-model="searchObj.name"
                          clearable
                          style="width: 300px;margin-right: 10px"
                          placeholder="请输入员工名称进行查询"
                          size="small" />
                <el-button @click="loadTableData"
                           class="change_btn"
                           icon="el-icon-search"
                           type="primary"
                           size="small">搜索</el-button>
            </div>
        </div>
        <div>
            <el-table :data="tableData"
                      stripe
                      size="small"
                      style="margin-top: 10px"
                      v-loading="loading"
                      element-loading-text="加载中..."
                      element-loading-spinner="fa fa-cog fa-spin">
                <el-table-column type="selection"
                                 align="center">
                </el-table-column>
                <el-table-column prop="workId"
                                 label="工号"
                                 align="center">

                </el-table-column>
                <el-table-column prop="name"
                                 label="名称"
                                 align="center">

                </el-table-column>
                <el-table-column prop="gender"
                                 label="性别"
                                 width="80"
                                 align="center">

                </el-table-column>
                <el-table-column prop="department.name"
                                 label="部门"
                                 align="center">

                </el-table-column>
                <el-table-column prop="workId"
                                 label="工资账套"
                                 align="center">
                    <template slot-scope="scope">
                        <el-tooltip placement="left"
                                    v-if="scope.row.salary">
                            <div slot="content">
                                <table>
                                    <tr>
                                        <td>基本工资:</td>
                                        <td>{{scope.row.salary.basicSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>奖金:</td>
                                        <td>{{scope.row.salary.bonus}}</td>
                                    </tr>
                                    <tr>
                                        <td>午餐补助:</td>
                                        <td>{{scope.row.salary.lunchSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>交通补助:</td>
                                        <td>{{scope.row.salary.trafficSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老基金数:</td>
                                        <td>{{scope.row.salary.pensionBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金比率:</td>
                                        <td>{{scope.row.salary.pensionPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗基数:</td>
                                        <td>{{scope.row.salary.medicalBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险比率:</td>
                                        <td>{{scope.row.salary.medicalPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金基数:</td>
                                        <td>{{scope.row.salary.accumulationFundBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金比率:</td>
                                        <td>{{scope.row.salary.accumulationFundPer}}</td>
                                    </tr>
                                </table>
                            </div>
                            <el-tag>{{scope.row.salary.name}}</el-tag>
                        </el-tooltip>
                        <el-tag v-else>暂未设置</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作"
                                 align="center"
                                 width="130px">
                    <template slot-scope="scope">
                        <el-popover placement="left"
                                    title="编辑工资账套"
                                    width="200"
                                    @hide="handleHide(scope.row)"
                                    trigger="click">
                            <div>
                                <div v-if="selectLoading">
                                    <i class="el-icon-loading"
                                       style="margin-right: 10px" /><span style="color: #808080">loading...</span>
                                </div>
                                <el-select v-else
                                           v-model="scope.row.salary.id"
                                           filterable
                                           size="small"
                                           @change="handleChange"
                                           placeholder="请选择工资账套">
                                    <el-option v-for="item in allSalary"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id">
                                    </el-option>
                                </el-select>
                            </div>
                            <el-button slot="reference"
                                       size="mini"
                                       icon="el-icon-edit"
                                       style="background-color: #13ce66;color: #FFF"
                                       @click="loadAllSalary(scope.$index, scope.row)"></el-button>
                        </el-popover>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页组件-->
            <el-pagination style="margin-top: 20px"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :page-sizes="[10, 20, 30, 50, 100, 500]"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="pageObj.total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "SalSobCfg",
    data () {
        return {
            //表格加载
            loading: false,
            selectLoading: false,
            //选择器是否发生改变
            selectChange: false,
            searchObj: {
                name: '',
                workId: ''
            },
            //表格数据
            tableData: [],
            //所有的工资账套数据
            allSalary: [],
            //分页对象
            pageObj: {
                //当前第几页
                page: 1,
                //每页显示多少条
                size: 10,
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
            this.getRequest('/salary/sobcfg/?name=' + this.searchObj.name + '&workId=' + this.searchObj.workId + '&page=' + this.pageObj.page + '&size=' + this.pageObj.size).then(resp => {
                this.tableData = resp.data;
                this.pageObj.total = resp.count;
                this.loading = false;
            })
        },
        // 获取所有的工资账套数据
        loadAllSalary () {
            this.selectLoading = true;
            this.getRequest('/salary/sob/salaries').then(resp => {
                if (resp) {
                    this.allSalary = resp;
                    this.selectLoading = false;
                }
            })
        },
        //编辑工资账套
        handleHide (data) {
            if (this.selectChange) {
                this.putRequest('/salary/sobcfg/?eid=' + data.id + '&sid=' + data.salary.id).then(resp => {
                    if (resp) {
                        this.loadTableData();
                    }
                })
            }
        },
        //选择器发送改变时触发
        handleChange () {
            this.selectChange = true;
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
/*按钮位置微调*/
.change_btn {
    position: relative;
    top: -1px;
}
</style>
