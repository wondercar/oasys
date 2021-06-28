<template>
  <div style="height:100%">
    <!--头部条件-->
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-button @click="handleAdd" icon="el-icon-document-add" size="small" type="primary" class="search_bth">添加</el-button>
        <el-button :disabled="multipleSelection.length <= 0" @click="batchDelete" type="danger" size="small" icon="el-icon-document-remove" style="position: relative;top: -1px">批量删除</el-button>
      </div>

      <div>
        <el-input
                class="searchDataInput"
                size="small"
                placeholder="输入职位名称"
                prefix-icon="el-icon-user-solid"
                @keydown.enter.native="handleSearch"
                clearable
                v-model="dataObj.name">
        </el-input>
        <el-button @click="handleSearch" icon="el-icon-search" size="small" type="primary" class="search_bth">搜索</el-button>
        <el-button @click="handleClear" icon="el-icon-refresh-right" size="small" type="primary" class="search_bth">重置</el-button>
      </div>
    </div>
    <!--表格数据-->
    <div style="margin-top : 15px;">
      <div style="height: 420px;overflow: auto">
        <el-table
                @selection-change="handleSelectionChange"
                :data="tableData"
                stripe
                size="small"
                :default-sort = "{prop: 'createDate', order: 'descending'}"
                v-loading="loading"
                element-loading-text="加载中..."
                element-loading-spinner="fa fa-cog fa-spin"
                style="width: 100%">
          <el-table-column
                  type="selection"
                  align="center"
                  width="55">
          </el-table-column>
          <el-table-column
                  prop="id"
                  label="编号"
                  align="center"
                  width="150px"
          >
          </el-table-column>
          <el-table-column
                  prop="name"
                  label="职位"
                  align="center"
          >
          </el-table-column>
          <el-table-column
                  prop="createDate"
                  sortable
                  align="center"
                  label="创建时间">
            <template slot-scope="scope">
              <i class="el-icon-time"/>
              <span style="margin-left: 10px">{{ scope.row.createDate }}</span>
            </template>
          </el-table-column>
          <el-table-column
                  prop="enabled"
                  align="center"
                  label="启用">
            <template slot-scope="scope">
              <el-tag size="medium" :type="scope.row.enabled ? 'success' : 'danger'">{{ scope.row.enabled ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
                  label="操作"
                  align="center"
                  width="200px">
            <template slot-scope="scope">
              <el-button
                      size="mini"
                      icon="el-icon-edit"
                      style="background-color: #5FB878;color: #FFF"
                      @click="handleEdit(scope.$index, scope.row)"></el-button>
              <el-button
                      size="mini"
                      type="danger"
                      icon="el-icon-delete"
                      style="background-color: #FF4351"
                      @click="handleDelete(scope.$index, scope.row)"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-pagination
              background
              style="margin-top:25px"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :page-sizes="[8, 16, 32, 64]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pageObj.total">
      </el-pagination>
    </div>

    <!--对话框区域-->
    <el-dialog
            :title= "this.optionStatus === 'add' ? '添加' : '修改'"
            :visible.sync="dialogVisible"
            top="15%"
            width="30%">
      <el-switch
              v-model="editDataObj.enabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用"
              style="margin: 0 auto 20px">
      </el-switch>
      <div>
        <el-input placeholder="请输入职位名称" ref="dataObjNameInput" v-model="editDataObj.name" @keydown.enter.native="dialogSubmit">
          <template slot="prepend">职位名称</template>
        </el-input>
      </div>
      <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      <el-button size="small" type="primary" @click="dialogSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "PosMana",
    data(){
      return {
        loading: false,
        //操作状态
        optionStatus: '',
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
        },
        //对话框添加或编辑的对象
        editDataObj: {
          name: '',
          enabled: true,
        },
        //表格数据
        tableData: [],
        //弹出层
        dialogVisible: false,
        //批量删除的对象
        multipleSelection: [],
      }
    },
    //组件渲染完成钩子 加载表格数据
    mounted() {
      this.loadTableData();
    },
    methods: {
      //加载表格数据
      loadTableData(){
        this.loading = true;
        this.getRequest('/system/basic/pos/?page=' + this.pageObj.page + '&size=' + this.pageObj.size + '&name=' + this.dataObj.name).then(resp => {
          if(resp){
            this.tableData = resp.data;
            this.pageObj.total = resp.count;
            this.loading = false;
          }
        })
      },
      //表头添加
      handleAdd(){
        //添加状态
        this.optionStatus = 'add';
        //显示对话框
        this.dialogVisible = true;
        //制空数据
        this.editDataObj.enabled = true;
        this.editDataObj.name = '';
      },
      //列编辑
      handleEdit(index,data){
        //添加状态
        this.optionStatus = 'edit';
        //给对话框赋值(拷贝一份数据，防止双向绑定时，用户输入的值跟随表单中的值发生变化)
        Object.assign(this.editDataObj,data);
        //显示对话框
        this.dialogVisible = true;
      },
      //列删除
      handleDelete(index,data){
        this.$confirm('是否删除数据id为'+ data.id +'的全部信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/basic/pos/' + data.id).then(resp => {
            if(resp){
              //刷新表格
              this.loadTableData();
              window.sessionStorage.removeItem('positions');
            }
          })
        })
      },
      //对话框确定按钮点击事件
      dialogSubmit(){
          if(this.editDataObj.name){
            //将日期设置为空，否则页面提交日期类型转换错误
            this.editDataObj.createDate = '';
            if('add' === this.optionStatus){
              this.postRequest('/system/basic/pos/',this.editDataObj).then(resp => {
                if(resp){
                  //刷新表格
                  this.loadTableData();
                  //关闭对话框
                  this.dialogVisible = false;
                  window.sessionStorage.removeItem('positions');
                }
              })
            } else if('edit' === this.optionStatus){
              this.putRequest('/system/basic/pos/',this.editDataObj).then(resp => {
                if (resp){
                  //刷新表格
                  this.loadTableData();
                  //关闭对话框
                  this.dialogVisible = false;
                  window.sessionStorage.removeItem('positions');
                }
              })
            }
          }else{
            this.$refs.dataObjNameInput.focus();
            this.$message.error('职位名称不能为空！');
          }
      },
      //表头搜索
      handleSearch(){
        this.loadTableData();
      },
      //表头重置
      handleClear(){
        this.dataObj.name = '';
        this.loadTableData();
      },
      //判断是否属于批量删除
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      //执行批量删除
      batchDelete(){
        this.$confirm('是否批量删除这'+ this.multipleSelection.length +'条数据的全部信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = '?';
          this.multipleSelection.forEach(item => {
            ids += 'ids=' + item.id + '&';
          })
          this.deleteRequest('/system/basic/pos/' + ids).then(resp => {
            if(resp){
              //刷新表格
              this.loadTableData();
              window.sessionStorage.removeItem('positions');
            }
          })
        })
      },
      //分页处理
      handleSizeChange(size){
        this.pageObj.size = size;
        this.loadTableData();
      },
      handleCurrentChange(currentPage){
        this.pageObj.page = currentPage;
        this.loadTableData();
      },
    }
  }
</script>

<style scoped>
  .searchDataInput{
    width: 300px;
    margin-right: 10px;
  }
  .search_bth{
    position: relative;
    top: -1px;
  }
</style>
