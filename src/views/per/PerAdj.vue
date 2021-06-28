<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input prefix-icon="el-icon-search"
                          placeholder="输入员工姓名搜索"
                          v-model="searchValue.name"
                          @keydown.enter.native="initData"
                          clearable
                          @clear="initData"
                          style="width: 200px;"></el-input>
                <el-button type="primary"
                           icon="el-icon-search"
                           @click="initData">搜索
                </el-button>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table :data="respData"
                      stripe
                      border
                      v-loading="loading"
                      element-loading-text="正在加载..."
                      element-loading-spinner="el-icon-loading"
                      element-loading-background="rgba(0, 0, 0, 0.8)"
                      style="width: 100%">
                <el-table-column type="selection">
                </el-table-column>
                <el-table-column prop="id"
                                 label="id"
                                 fixed
                                 width="80"
                                 align="center">
                </el-table-column>
                <el-table-column prop="employee.workId"
                                 label="工号"
                                 fixed
                                 width="180"
                                 align="center">
                </el-table-column>
                <el-table-column prop="employee.name"
                                 label="姓名"
                                 fixed
                                 width="80"
                                 align="center">
                </el-table-column>
                <el-table-column prop="beforeDep.name"
                                 label="调动前部门"
                                 align="center" />
                <el-table-column prop="beforePos.name"
                                 label="调动前职位"
                                 align="center" />
                <el-table-column prop="afterDep.name"
                                 label="调动后部门"
                                 align="center">
                </el-table-column>
                <el-table-column prop="afterPos.name"
                                 label="调动后职位"
                                 align="center" />
                <el-table-column prop="beforeSalary.allSalary"
                                 label="调动前工资"
                                 align="center" />
                <el-table-column prop="afterSalary.allSalary"
                                 label="调动后工资"
                                 align="center" />
                <el-table-column prop="reason"
                                 label="调动原因"
                                 align="center" />
                <el-table-column prop="removeDate"
                                 align="center"
                                 label="调动日期">
                </el-table-column>
                <el-table-column align="center"
                                 label="操作">
                    <template slot-scope="scope">
                        <el-button @click="handleEdit(scope.row)"
                                   icon="el-icon-edit"
                                   size="mini"
                                   style="background-color: #13ce66;color: #FFF"
                                   title="修改"
                                   type="warn">
                        </el-button>
                        <el-button @click="handleDelete(scope.row)"
                                   icon="el-icon-delete"
                                   size="mini"
                                   style="background-color: #ff4949"
                                   title="删除"
                                   type="danger">
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination background
                               @current-change="currentChange"
                               @size-change="sizeChange"
                               layout="sizes,prev,pager,next,jumper,->,total,slot"
                               :total="total"></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PerMv",
    data () {
        return {
            loading: false,
            total: 0,
            respData: null,
            currentPage: 1,
            size: 10,
            searchValue: {
                name: '',
                beginDateScope: []
            }
        }
    },
    create () {

    },
    mounted () {
        this.initData();
    },
    methods: {
        sizeChange (currentSize) {
            this.size = currentSize;
            this.initData();
        },
        currentChange (currentPage) {
            this.currentPage = currentPage;
            this.initData();
        },
        initData () {
            this.loading = true;
            let url = '/employee/basic/getAllEmpAdj/?page=' + this.currentPage + '&size=' + this.size;
            // if (this.searchValue.name) {
            //     url += '&name=' + this.searchValue.name;
            // }
            // if (this.searchValue.beginDateScope) {
            //     url += '&beginDateScope=' + this.searchValue.beginDateScope;
            // }
            this.getRequest(url).then(resp => {
                this.loading = false;
                console.log(resp.data);
                if (resp) {
                    this.respData = resp.data;
                    this.total = resp.total;
                }
            })
        },
        handleDelete (data) {
            this.$confirm('此操作将永久删除该条：【' + data.employee.name + '】的调动记录，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRequest('/employee/basic/delEmpAdj?id=' + data.id).then(resp => {
                    if (resp) {
                        this.initData();
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        handleEdit () {

        }
    }
}
</script>

<style scoped>
</style>
