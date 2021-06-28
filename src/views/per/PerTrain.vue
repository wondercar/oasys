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
                                 align="center">
                </el-table-column>
                <el-table-column prop="employee.workId"
                                 label="工号"
                                 align="center">
                </el-table-column>
                <el-table-column prop="employee.id"
                                 label="员工姓名"
                                 align="center">
                </el-table-column>
                <el-table-column prop="department.id"
                                 label="部门编号"
                                 align="center">
                </el-table-column>
                <el-table-column prop="department.name"
                                 label="部门名称"
                                 align="center">
                </el-table-column>
                <el-table-column prop="trainContent"
                                 label="培训内容"
                                 align="center">
                </el-table-column>
                <el-table-column prop="trainDate"
                                 align="center"
                                 label="培训日期">
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
    name: "PerEc",
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
            let url = '/employee/basic/getAllEmpTrain?page=' + this.currentPage + '&size=' + this.size;
            // if (this.searchValue.name) {
            //     url += '&name=' + this.searchValue.name;
            // }
            // if (this.searchValue.beginDateScope) {
            //     url += '&beginDateScope=' + this.searchValue.beginDateScope;
            // }
            this.getRequest(url).then(resp => {
                this.loading = false;
                if (resp) {
                    console.log(resp)
                    this.respData = resp.data;
                    this.total = resp.total;
                }
            })
        },
        handleDelete (data) {
            this.$confirm('此操作将永久删除该条：【' + data.employee.name + '】, 的培训记录，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.getRequest('/employee/basic/delEmpTrain?id=' + data.id).then(resp => {
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
        hadnleEdit () {

        }
    }
}
</script>

<style scoped>
</style>
