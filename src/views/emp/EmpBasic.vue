<template>
    <div>
        <!--头部-->
        <div class="header_content">
            <!--头部搜索区域-->
            <div>
                <el-input style="width: 250px;margin-left: 5px"
                          @keydown.enter.native="loadEmpData"
                          clearable
                          @clear="loadEmpData"
                          v-model="searchObj.workId"
                          placeholder="输入员工工号进行查询"
                          :disabled="showAdvSearchVisible"
                          size="small" />
                <el-button @click="loadEmpData"
                           size="small"
                           icon="el-icon-search"
                           type="primary"
                           style="margin-left: 10px"
                           :disabled="showAdvSearchVisible"
                           class="change_btn">搜索</el-button>
                <el-button @click="handleAdvSearchBtn"
                           size="small"
                           style="margin-left: 10px"
                           class="change_btn"
                           type="primary">
                    <i :class="showAdvSearchVisible ? 'fa fa-caret-up' : 'fa fa-caret-down'"
                       style="margin-right: 5px"
                       aria-hidden="true" />
                    高级搜索
                </el-button>
            </div>
            <!--头部导出按钮区域-->
            <div style="margin-right: 5px">
                <el-button @click="handleAdd"
                           type="primary"
                           size="small"
                           class="change_btn"><i class="header_i fa fa-plus" />添加</el-button>
                <el-upload :show-file-list="false"
                           style="display: inline-flex;margin: 0 10px 0 10px"
                           class="upload-demo"
                           :headers="headers"
                           :before-upload="beforeUpload"
                           :on-success="uploadOnSuccess"
                           :on-error="uploadOnError"
                           :disabled="importDataDisabled"
                           action="/employee/basic/import">
                    <el-button :disabled="importDataDisabled"
                               type="success"
                               size="small"
                               class="change_btn"
                               :icon="importDataBtnIcon">{{importDataBntText}}</el-button>
                </el-upload>
                <el-button @click="exportData"
                           type="success"
                           size="small"
                           class="change_btn"
                           icon="el-icon-upload2">导出</el-button>
            </div>
        </div>

        <!--高级搜索区域-->
        <transition name="slide-fade">
            <div v-show="showAdvSearchVisible"
                 class="advSearch_content">
                <el-form :model="optionObj"
                         ref="searchForm"
                         label-width="120px">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号:">
                                <el-input v-model="searchObj.workId"
                                          prefix-icon="el-icon-user-solid"
                                          placeholder="请输入工号"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="名称:">
                                <el-input v-model="searchObj.name"
                                          prefix-icon="el-icon-user"
                                          placeholder="请输入名称"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="性别:">
                                <el-radio-group v-model="searchObj.gender">
                                    <el-radio label="男"></el-radio>
                                    <el-radio label="女"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="身份证号:">
                                <el-input v-model="searchObj.idCard"
                                          prefix-icon="el-icon-user"
                                          placeholder="请输入身份证号"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="婚姻状况:">
                                <el-select v-model="searchObj.wedlock"
                                           clearable
                                           placeholder="请选择婚姻状况"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in wedlocks"
                                               :key="item"
                                               :label="item"
                                               :value="item"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="籍贯:">
                                <el-input v-model="searchObj.nationPlace"
                                          prefix-icon="el-icon-edit"
                                          placeholder="请输入籍贯"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="手机号码:">
                                <el-input v-model="searchObj.phone"
                                          prefix-icon="el-icon-phone"
                                          placeholder="请输入手机号码"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系地址:">
                                <el-input v-model="searchObj.address"
                                          prefix-icon="fa fa-address-card-o"
                                          placeholder="请输入联系地址"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="部门:">
                                <el-popover placement="right"
                                            title="(点击箭头伸缩,点击节点选中)"
                                            width="500"
                                            trigger="click"
                                            v-model="searchPopoverVisible">
                                    <el-tree :data="departments"
                                             :props="defaultProps"
                                             default-expand-all
                                             @node-click="searchHandleNodeClick">
                                        <span class="custom-tree-node"
                                              slot-scope="{ node, data }">
                                            <span>{{ node.label }}</span>
                                        </span>
                                    </el-tree>
                                    <el-input slot="reference"
                                              clearable
                                              @clear="searchObj.departmentId = ''"
                                              clearable
                                              placeholder="点击选择部门"
                                              v-model="departmentName"
                                              style="width: 180px"
                                              size="small" />
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="民族:">
                                <el-select v-model="searchObj.nationId"
                                           clearable
                                           filterable
                                           placeholder="请选择民族"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in nations"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="政治面貌:">
                                <el-select v-model="searchObj.politicId"
                                           clearable
                                           filterable
                                           placeholder="请选择政治面貌"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in politicsStatus"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="职称:">
                                <el-select v-model="searchObj.jobLevelId"
                                           clearable
                                           filterable
                                           placeholder="请选择职称"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in joblevels"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"
                                               :disabled="!item.enabled"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="7">
                            <el-form-item label="聘用形式:">
                                <el-radio-group v-model="searchObj.engageForm">
                                    <el-radio label="劳动合同"></el-radio>
                                    <el-radio label="劳务合同"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="职位:">
                                <el-select v-model="searchObj.posId"
                                           clearable
                                           filterable
                                           placeholder="请选择职称"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in positions"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"
                                               :disabled="!item.enabled"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="合同起始日期:">
                                <el-date-picker v-model="searchObj.beginDateScope"
                                                type="daterange"
                                                align="right"
                                                unlink-panels
                                                value-format="yyyy-MM-dd"
                                                range-separator="至"
                                                start-placeholder="开始日期"
                                                end-placeholder="结束日期"
                                                size="small">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24"
                                :offset="10">
                            <el-button size="small"
                                       @click="handleAdvSearchBtn">取消</el-button>
                            <el-button @click="loadEmpData('adv')"
                                       type="primary"
                                       icon="el-icon-search"
                                       size="small">搜索</el-button>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
        </transition>

        <!--数据表格-->
        <div class="table_content">
            <el-table :data="empList"
                      stripe
                      size="small"
                      :default-sort="{prop: 'beginDate', order: 'descending'}"
                      v-loading="loading"
                      element-loading-text="加载中..."
                      element-loading-spinner="fa fa-cog fa-spin"
                      style="width: 100%">
                <el-table-column prop="workId"
                                 label="工号"
                                 fixed
                                 width="140px"
                                 align="center">
                </el-table-column>
                <el-table-column fixed
                                 prop="name"
                                 label="名称"
                                 align="center">
                </el-table-column>
                <el-table-column prop="gender"
                                 label="性别"
                                 width="50px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="birthday"
                                 label="生日"
                                 width="90px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="idCard"
                                 label="身份证号"
                                 width="150px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="wedlock"
                                 label="婚姻状况"
                                 width="70px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="nation.name"
                                 label="民族"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="nationPlace"
                                 label="籍贯"
                                 width="120px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="politicsStatus.name"
                                 label="政治面貌"
                                 width="150px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="email"
                                 label="邮箱"
                                 width="150px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="phone"
                                 label="手机号"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="address"
                                 label="联系地址"
                                 width="200px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="department.name"
                                 label="部门"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="position.name"
                                 label="职位"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="joblevel.name"
                                 label="职称"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="engageForm"
                                 label="聘用形式"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="tiptopDegree"
                                 label="最高学历"
                                 width="80px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="school"
                                 label="毕业院校"
                                 width="150px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="specialty"
                                 label="专业"
                                 width="150px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="workState"
                                 label="在职状态"
                                 width="80px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="beginDate"
                                 sortable
                                 align="center"
                                 width="120px"
                                 label="入职日期">
                    <template slot-scope="scope">
                        <i class="el-icon-time" />
                        <span style="margin-left: 10px">{{ scope.row.beginDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="conversionTime"
                                 label="转正日期"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="beginContract"
                                 label="合同起始日期"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="endContract"
                                 label="合同截止日期"
                                 width="100px"
                                 align="center">
                </el-table-column>
                <el-table-column prop="contractTerm"
                                 label="合同期限"
                                 width="120px"
                                 align="center">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}天</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作"
                                 align="center"
                                 fixed="right"
                                 width="180px">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   icon="el-icon-edit"
                                   title="编辑"
                                   style="background-color: #13ce66;color: #FFF"
                                   @click="handleEdit(scope.$index, scope.row)"></el-button>
                        <el-button size="mini"
                                   type="danger"
                                   icon="el-icon-delete"
                                   title="删除"
                                   style="background-color: #ff4949"
                                   @click="handleDelete(scope.$index, scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页控件-->
            <el-pagination @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :page-sizes="[10, 20, 30, 100, 500, 1000]"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="pageObj.total"
                           style="margin-top: 5px"
                           size="small">
            </el-pagination>
        </div>
        <el-dialog :title="optionStatus === '添加'?'添加员工':'编辑员工'"
                   :visible.sync="dialogVisible"
                   @close="closeDialog"
                   width="90%"
                   size="small">
            <!--弹出框主体-->
            <div>
                <el-form :model="optionObj"
                         :rules="rules"
                         ref="ruleForm"
                         label-width="120px">
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="工号:"
                                          prop="workId">
                                <el-input v-model="optionObj.workId"
                                          disabled
                                          prefix-icon="el-icon-user-solid"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="名称:"
                                          prop="name">
                                <el-input v-model="optionObj.name"
                                          prefix-icon="el-icon-user"
                                          placeholder="请输入名称"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="性别:"
                                          prop="gender">
                                <el-radio-group v-model="optionObj.gender">
                                    <el-radio label="男"></el-radio>
                                    <el-radio label="女"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生日期:"
                                          prop="birthday">
                                <el-date-picker type="date"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择日期"
                                                v-model="optionObj.birthday"
                                                style="width: 180px;"
                                                size="small"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="身份证号:"
                                          prop="idCard">
                                <el-input v-model="optionObj.idCard"
                                          placeholder="请输入身份证号"
                                          clearable
                                          prefix-icon="fa fa-address-card-o"
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="婚姻状况:"
                                          prop="wedlock">
                                <el-select v-model="optionObj.wedlock"
                                           placeholder="请选择婚姻状况"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in wedlocks"
                                               :key="item"
                                               :label="item"
                                               :value="item"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="民族:"
                                          prop="nationId">
                                <el-select v-model="optionObj.nationId"
                                           clearable
                                           filterable
                                           placeholder="请选择民族"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in nations"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="籍贯:"
                                          prop="nationPlace">
                                <el-input v-model="optionObj.nationPlace"
                                          prefix-icon="el-icon-edit"
                                          placeholder="请输入籍贯"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="部门:"
                                          prop="departmentId">
                                <el-popover placement="right"
                                            title="(点击箭头伸缩,点击节点选中)"
                                            width="500"
                                            trigger="click"
                                            v-model="popoverVisible">
                                    <el-tree :data="departments"
                                             :props="defaultProps"
                                             default-expand-all
                                             @node-click="handleNodeClick">
                                        <span class="custom-tree-node"
                                              slot-scope="{ node, data }">
                                            <span>{{ node.label }}</span>
                                        </span>
                                    </el-tree>
                                    <el-input slot="reference"
                                              placeholder="点击选择部门"
                                              v-model="departmentName"
                                              style="width: 180px"
                                              size="small" />
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="邮箱:"
                                          prop="email">
                                <el-input v-model="optionObj.email"
                                          prefix-icon="el-icon-message"
                                          placeholder="请输入邮箱"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="手机号码:"
                                          prop="phone">
                                <el-input v-model="optionObj.phone"
                                          prefix-icon="el-icon-phone"
                                          placeholder="请输入手机号码"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系地址:"
                                          prop="address">
                                <el-input v-model="optionObj.address"
                                          prefix-icon="fa fa-address-card-o"
                                          placeholder="请输入联系地址"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="政治面貌:"
                                          prop="politicId">
                                <el-select v-model="optionObj.politicId"
                                           clearable
                                           filterable
                                           placeholder="请选择政治面貌"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in politicsStatus"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="职称:"
                                          prop="jobLevelId">
                                <el-select v-model="optionObj.jobLevelId"
                                           clearable
                                           filterable
                                           placeholder="请选择职称"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in joblevels"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"
                                               :disabled="!item.enabled"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="职位:"
                                          prop="posId">
                                <el-select v-model="optionObj.posId"
                                           clearable
                                           filterable
                                           placeholder="请选择职称"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in positions"
                                               :key="item.id"
                                               :label="item.name"
                                               :value="item.id"
                                               :disabled="!item.enabled"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="聘用形式:"
                                          prop="engageForm">
                                <el-radio-group v-model="optionObj.engageForm">
                                    <el-radio label="劳动合同"></el-radio>
                                    <el-radio label="劳务合同"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="最高学历:"
                                          prop="tiptopDegree">
                                <el-select v-model="optionObj.tiptopDegree"
                                           placeholder="请选择最高学历"
                                           size="small"
                                           style="width: 180px">
                                    <el-option v-for="item in tiptopDegrees"
                                               :key="item"
                                               :label="item"
                                               :value="item"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属专业:"
                                          prop="specialty">
                                <el-input v-model="optionObj.specialty"
                                          prefix-icon="el-icon-edit"
                                          placeholder="请输入所属专业"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校:"
                                          prop="school">
                                <el-input v-model="optionObj.school"
                                          prefix-icon="el-icon-edit"
                                          placeholder="请输入毕业院校"
                                          clearable
                                          size="small"
                                          style="width: 180px" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="在职状态:"
                                          prop="workState">
                                <el-radio-group :disabled="optionStatus==='添加'"
                                                v-model="optionObj.workState">
                                    <el-radio label="在职"></el-radio>
                                    <el-radio label="离职"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-form-item label="入职日期:"
                                          prop="beginDate">
                                <el-date-picker type="date"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择日期"
                                                v-model="optionObj.beginDate"
                                                style="width: 180px;"
                                                size="small"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="转正日期:"
                                          prop="conversionTime">
                                <el-date-picker type="date"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择日期"
                                                v-model="optionObj.conversionTime"
                                                style="width: 180px;"
                                                size="small"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始日期:"
                                          prop="beginContract">
                                <el-date-picker type="date"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择日期"
                                                v-model="optionObj.beginContract"
                                                style="width: 180px;"
                                                size="small"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同截止日期:"
                                          prop="endContract">
                                <el-date-picker type="date"
                                                value-format="yyyy-MM-dd"
                                                placeholder="选择日期"
                                                v-model="optionObj.endContract"
                                                style="width: 180px;"
                                                size="small"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer"
                  class="dialog-footer">
                <el-button size="small"
                           @click="dialogVisible = false">取 消</el-button>
                <el-button size="small"
                           type="primary"
                           :disabled="dialogSubmitDisabled"
                           @click="handleDialogSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { Notification } from "element-ui";

export default {
    name: "EmpBasic",
    data () {
        return {
            loading: false,
            //操作状态
            optionStatus: '',
            //提交按钮禁用
            dialogSubmitDisabled: '',
            //高级搜索部门选择面板显隐
            searchPopoverVisible: false,
            //部门选择面板显隐
            popoverVisible: false,
            departmentName: '',
            //员工高级搜索区域显影
            showAdvSearchVisible: false,
            //搜索对象
            searchObj: {
                name: '',
                workId: '',
                gender: '',
                idCard: '',
                wedlock: '',
                nationPlace: '',
                phone: '',
                address: '',
                departmentId: '',
                nationId: '',
                politicId: '',
                jobLevelId: '',
                engageForm: '',
                posId: '',
                beginDateScope: ''
            },
            optionObj: {
                name: '',
                gender: '',
                birthday: '',
                idCard: '',
                wedlock: '',
                nationId: '',
                nationPlace: '',
                politicId: '',
                email: '',
                phone: '',
                address: '',
                departmentId: '',
                jobLevelId: '',
                posId: '',
                engageForm: '',
                tiptopDegree: '',
                specialty: '',
                school: '',
                beginDate: '',
                workState: '在职',
                workId: '',
                contractTerm: '',
                conversionTime: '',
                notWorkTract: '',
                beginContract: '',
                endContract: '',
                workAge: '',
                salaryId: ''
            },
            dialogVisible: false,
            //添加修改面板下拉框数据
            //民族
            nations: [],
            //职称
            joblevels: [],
            //政治面貌
            politicsStatus: [],
            //职位
            positions: [],
            //部门
            departments: [],
            defaultProps: {
                children: 'children',
                label: 'name',
            },
            wedlocks: ['未婚', '已婚', '离异'],
            tiptopDegrees: ['硕士', '博士', '本科', '大专', '高中', '初中', '小学', '其他'],
            //所有emp集合
            empList: [],
            rules: {
                name: [{ required: true, message: "请输入姓名", trigger: "change" }],
                gender: [{ required: true, message: "请选择性别", trigger: "change" }],
                birthday: [{ required: true, message: "请输入出生日期", trigger: "change" }],
                idCard: [{ required: true, message: "请输入身份证号", trigger: "change" },
                { pattern: /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d|X|x)$/, message: "身份证号格式不正确", trigger: "change" }
                ],
                wedlock: [{ required: true, message: "请选择婚姻状况", trigger: "change" }],
                nationId: [{ required: true, message: "请选择民族", trigger: "change" }],
                nationPlace: [{ required: true, message: "请输入籍贯", trigger: "change" }],
                departmentId: [{ required: true, message: "请选择部门", trigger: "change" }],
                email: [{ required: true, message: "请输入邮箱", trigger: "change" }, { type: 'email', message: '邮箱格式不正确', trigger: 'change' }],
                phone: [{ required: true, message: "请输入手机号", trigger: "change" }],
                address: [{ required: true, message: "请输入联系地址", trigger: "change" }],
                politicId: [{ required: true, message: "请选择政治面貌", trigger: "change" }],
                jobLevelId: [{ required: true, message: "请选择职称", trigger: "change" }],
                posId: [{ required: true, message: "请选择职位", trigger: "change" }],
                engageForm: [{ required: true, message: "请选择聘用形式", trigger: "change" }],
                tiptopDegree: [{ required: true, message: "请选择最高学历", trigger: "change" }],
                //specialty:[{required:true,message:"请输入所属专业",trigger:"change"}],
                //school:[{required:true,message:"请输入毕业院校",trigger:"change"}],
                beginDate: [{ required: true, message: "请输入入职日期", trigger: "change" }],
                conversionTime: [{ required: true, message: "请输入转正日期", trigger: "change" }],
                beginContract: [{ required: true, message: "请输入合同起始日期", trigger: "change" }],
                endContract: [{ required: true, message: "请输入合同截止日期", trigger: "change" }],
            },
            //数据导入
            //文件上传头部信息添加token
            headers: {
                Authorization: window.sessionStorage.getItem('tokenStr')
            },
            importDataBntText: '导入',
            importDataBtnIcon: 'el-icon-download',
            importDataDisabled: false,
            //分页对象
            pageObj: {
                //当前第几页
                page: 1,
                //每页显示多少条
                size: 10,
                //总记录数
                total: 0,
            },
        }
    },
    mounted () {
        this.loadEmpData();
        this.initData();
    },
    methods: {
        //加载数据
        loadEmpData (type) {
            this.loading = true;
            // 是否是高级搜索
            if (type && 'adv' === type) {
                this.pageObj.page = 1;
            }
            // 分页查询员工数据
            let url = '/employee/basic/?page=' + this.pageObj.page + '&size=' + this.pageObj.size + '&workId=' + this.searchObj.workId;

            // 拼接条件，条件不为空，就添加上然后进行搜索
            if (this.searchObj.name) {
                url += '&name=' + this.searchObj.name;
            }
            if (this.searchObj.gender) {
                url += '&gender=' + this.searchObj.gender;
            }
            if (this.searchObj.idCard) {
                url += '&idCard=' + this.searchObj.idCard;
            }
            if (this.searchObj.wedlock) {
                url += '&wedlock=' + this.searchObj.wedlock;
            }
            if (this.searchObj.nationPlace) {
                url += '&nationPlace=' + this.searchObj.nationPlace;
            }
            if (this.searchObj.phone) {
                url += '&phone=' + this.searchObj.phone;
            }
            if (this.searchObj.address) {
                url += '&address=' + this.searchObj.address;
            }
            if (this.searchObj.departmentId) {
                url += '&departmentId=' + this.searchObj.departmentId;
            }
            if (this.searchObj.nationId) {
                url += '&nationId=' + this.searchObj.nationId;
            }
            if (this.searchObj.politicId) {
                url += '&politicId=' + this.searchObj.politicId;
            }
            if (this.searchObj.jobLevelId) {
                url += '&jobLevelId=' + this.searchObj.jobLevelId;
            }
            if (this.searchObj.engageForm) {
                url += '&engageForm=' + this.searchObj.engageForm;
            }
            if (this.searchObj.posId) {
                url += '&posId=' + this.searchObj.posId;
            }
            if (this.searchObj.beginDateScope) {
                url += '&beginDateScope=' + this.searchObj.beginDateScope;
            }
            this.getRequest(url).then(resp => {
                if (resp) {
                    // console.log(resp.data);
                    this.empList = resp.data;
                    this.pageObj.total = resp.count;
                    this.loading = false;
                }
            });
        },
        initData () {
            //获取所有民族
            if (!window.sessionStorage.getItem('nations')) {
                this.getRequest('/employee/basic/getAllNation').then(resp => {
                    if (resp) {
                        this.nations = resp;
                        //存入数据
                        window.sessionStorage.setItem('nations', JSON.stringify(resp));
                    }
                })
            } else {
                this.nations = JSON.parse(window.sessionStorage.getItem('nations'));
            }

            //获取所有职称
            if (!window.sessionStorage.getItem('joblevels')) {
                this.getRequest('/system/basic/joblevel/getAllJoblevels').then(resp => {
                    if (resp) {
                        this.joblevels = resp;
                        //存入数据
                        window.sessionStorage.setItem('joblevels', JSON.stringify(resp));
                    }
                })
            } else {
                this.joblevels = JSON.parse(window.sessionStorage.getItem('joblevels'));
            }

            //获取所有政治面貌
            if (!window.sessionStorage.getItem('politicsStatus')) {
                this.getRequest('/employee/basic/getAllPoliticsStatus').then(resp => {
                    if (resp) {
                        this.politicsStatus = resp;
                        //存入数据
                        window.sessionStorage.setItem('politicsStatus', JSON.stringify(resp));
                    }
                })
            } else {
                this.politicsStatus = JSON.parse(window.sessionStorage.getItem('politicsStatus'));
            }

            //获取所有职位
            if (!window.sessionStorage.getItem('positions')) {
                this.getRequest('/system/basic/pos/getAllPositions').then(resp => {
                    if (resp) {
                        this.positions = resp;
                        //存入数据
                        window.sessionStorage.setItem('positions', JSON.stringify(resp));
                    }
                })
            } else {
                this.positions = JSON.parse(window.sessionStorage.getItem('positions'));
            }

            //获取所有部门
            if (!window.sessionStorage.getItem('departments')) {
                this.getRequest('/system/basic/department/').then(resp => {
                    if (resp) {
                        this.departments = resp;
                        //存入数据
                        window.sessionStorage.setItem('departments', JSON.stringify(resp));
                    }
                })
            } else {
                this.departments = JSON.parse(window.sessionStorage.getItem('departments'));
            }
        },
        // 获取工号
        getWorkID () {
            this.optionObj.workId = '正在获取...';
            this.getRequest('/employee/basic/getWorkID').then(resp => {
                if (resp) {
                    this.optionObj.workId = resp;
                }
            })
        },
        // 处理添加
        handleAdd () {
            this.dialogVisible = true;

            this.optionObj = {
                id: undefined,
                name: '',
                gender: '',
                birthday: '',
                idCard: '',
                wedlock: '',
                nationId: '',
                nationPlace: '',
                politicId: '',
                email: '',
                phone: '',
                address: '',
                departmentId: '',
                jobLevelId: '',
                posId: '',
                engageForm: '',
                tiptopDegree: '',
                specialty: '',
                school: '',
                beginDate: '',
                workState: '在职',
                workId: '',
                contractTerm: '',
                conversionTime: '',
                notWorkTract: '',
                beginContract: '',
                endContract: '',
                workAge: '',
                salaryId: ''
            };
            /*this.$nextTick(()=>{
              this.$refs['ruleForm'].resetFields();
            });*/
            this.departmentName = '';
            this.optionStatus = '添加';
            this.dialogSubmitDisabled = false;
            this.getWorkID();
        },

        /*对话框关闭事件*/
        closeDialog () {
            this.dialogVisible = false;
            // 点击取消 数据重置
            this.departmentName = '';
            this.$nextTick(() => {
                this.$refs['ruleForm'].resetFields();
            });
        },

        //处理编辑
        handleEdit (index, data) {
            this.dialogSubmitDisabled = false;
            this.$nextTick(() => {
                Object.assign(this.optionObj, data);
            });
            this.departmentName = data.department.name;
            this.optionStatus = '编辑';
            this.initData();
            this.dialogVisible = true;
        },

        handleDelete (index, data) {
            this.$confirm('是否删除工号为[' + data.workId + ']名称为[' + data.name + ']的员工信息?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRequest('/employee/basic/' + data.id).then(resp => {
                    if (resp) {
                        //刷新表格
                        this.loadEmpData();
                    }
                })
            })
        },
        //dialog提交事件
        handleDialogSubmit () {
            this.$refs['ruleForm'].validate(valid => {
                if (valid) {
                    this.dialogSubmitDisabled = true;
                    if ('添加' === this.optionStatus) {
                        this.postRequest('/employee/basic/', this.optionObj).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.loadEmpData();
                            }
                            this.dialogSubmitDisabled = false;
                        })
                    } else if ('编辑' === this.optionStatus) {
                        this.putRequest('/employee/basic/', this.optionObj).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.loadEmpData();
                            }
                            this.dialogSubmitDisabled = false;
                        })
                    }
                }
            })
        },

        //上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传
        beforeUpload (file) {
            this.importDataDisabled = true;
            this.importDataBntText = '正在导入';
            this.importDataBtnIcon = 'el-icon-loading';
        },
        //文件上传成功时的钩子
        uploadOnSuccess (response, file, fileList) {
            this.importDataDisabled = false;
            this.importDataBntText = '导入';
            this.importDataBtnIcon = 'el-icon-download';
            this.loadEmpData();
            Notification.success({
                title: '数据导入成功！',
                position: 'bottom-right'
            })
        },
        //文件上传失败时的钩子
        uploadOnError (err, file, fileList) {
            this.importDataDisabled = false;
            this.importDataBntText = '导入';
            this.importDataBtnIcon = 'el-icon-download';
            //失败通知
            Notification.error({
                title: '数据导入失败，请重试！',
                position: 'bottom-right'
            })
        },
        //数据导出
        exportData () {
            this.downloadRequest('/employee/basic/export');
        },

        //高级按钮点击事件
        handleAdvSearchBtn () {
            this.showAdvSearchVisible = !this.showAdvSearchVisible;
            if (!this.showAdvSearchVisible) {
                this.departmentName = '';
                this.searchObj = {
                    name: '',
                    workId: '',
                    gender: '',
                    idCard: '',
                    wedlock: '',
                    nationPlace: '',
                    phone: '',
                    address: '',
                    departmentId: '',
                    nationId: '',
                    politicId: '',
                    jobLevelId: '',
                    engageForm: '',
                    posId: '',
                    beginDateScope: ''
                };
                this.loadEmpData();
            }
        },
        //高级搜索部门树形节点点击事件
        searchHandleNodeClick (data) {
            //回显名称
            this.departmentName = data.name;
            this.searchObj.departmentId = data.id;
            this.searchPopoverVisible = !this.searchPopoverVisible;
        },
        //部门树形节点点击事件
        handleNodeClick (data) {
            //回显名称
            this.departmentName = data.name;
            //回显id
            this.optionObj.departmentId = data.id;
            this.popoverVisible = false;
        },
        //分页处理
        handleSizeChange (size) {
            this.pageObj.size = size;
            this.loadEmpData();
        },
        handleCurrentChange (currentPage) {
            this.pageObj.page = currentPage;
            this.loadEmpData();
        },
    }
}
</script>

<style scoped>
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
    transition: all 0.3s ease;
}

.slide-fade-leave-active {
    transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter, .slide-fade-leave-to
    /* .slide-fade-leave-active for below version 2.1.8 */ {
    transform: translateX(10px);
    opacity: 0;
}

/*头部*/
.header_content {
    display: flex;
    justify-content: space-between;
}

/*头部按钮图标*/
.header_i {
    margin-right: 5px;
}

/*高级搜索区域*/
.advSearch_content {
    font-size: 12px;
    border: 1px solid #cccccc;
    border-radius: 3px;
    box-sizing: border-box;
    margin: 10px 0 10px 0;
    padding: 15px 0 15px 0;
}

.table_content {
    overflow: auto;
    margin-top: 5px;
}
/*按钮位置微调*/
.change_btn {
    position: relative;
    top: -1px;
}
</style>
