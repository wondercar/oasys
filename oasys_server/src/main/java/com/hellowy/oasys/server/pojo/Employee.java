package com.hellowy.oasys.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工实体类
 *
 * @author wondercar
 * @date 2021/2/8
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "name")
@Accessors(chain = true)
@TableName("t_employee")
@ApiModel(value = "员工实体类", description = "")
public class Employee implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @NonNull
    @Excel(name = "姓名")
    @ApiModelProperty("名称")
    private String name;

    @Excel(name = "性别")
    @ApiModelProperty("性别")
    private String gender;

    @Excel(name = "生日", format = "yyyy-MM-dd", width = 20)
    @ApiModelProperty("生日")
    private Date birthday;

    @Excel(name = "身份证号", width = 20)
    @ApiModelProperty("身份证号")
    private String idCard;

    @Excel(name = "婚姻状况")
    @ApiModelProperty("婚姻状况")
    private String wedlock;

    @ApiModelProperty("民族id")
    private Integer nationId;

    @Excel(name = "籍贯")
    @ApiModelProperty("籍贯")
    private String nationPlace;

    @ApiModelProperty("政治面貌")
    private Integer politicId;

    @Excel(name = "邮箱", width = 30)
    @ApiModelProperty("邮箱")
    private String email;

    @Excel(name = "电话", width = 20)
    @ApiModelProperty("电话")
    private String phone;

    @Excel(name = "联系地址", width = 50)
    @ApiModelProperty("联系地址")
    private String address;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    @ApiModelProperty("职称id")
    private Integer jobLevelId;

    @ApiModelProperty("职位id")
    private Integer posId;

    @Excel(name = "聘用形式")
    @ApiModelProperty("聘用形式")
    private String engageForm;

    @Excel(name = "最高学历")
    @ApiModelProperty("最高学历")
    private String tiptopDegree;

    @Excel(name = "所属专业", width = 20)
    @ApiModelProperty("所属专业")
    private String specialty;

    @Excel(name = "毕业院校", width = 20)
    @ApiModelProperty("毕业院校")
    private String school;

    @Excel(name = "入职日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty("入职日期")
    private Date beginDate;

    @Excel(name = "在职状态")
    @ApiModelProperty("在职状态")
    private String workState;

    @Excel(name = "工号", width = 30)
    @ApiModelProperty("工号")
    private String workId;

    @Excel(name = "合同期限", suffix = "天")
    @ApiModelProperty("合同期限")
    private Double contractTerm;

    @Excel(name = "转正日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty("转正日期")
    private Date conversionTime;

    //@Excel(name = "离职日期",width = 20,format = "yyyy-MM-dd")
    @ApiModelProperty("离职日期")
    private Date notWorkTract;

    @Excel(name = "合同起始日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty("合同起始日期")
    private Date beginContract;

    @Excel(name = "合同终止日期", width = 20, format = "yyyy-MM-dd")
    @ApiModelProperty("合同终止日期")
    private Date endContract;

    @ApiModelProperty("工龄")
    private Double workAge;

    @ApiModelProperty("工资账套id")
    private Integer salaryId;

    @ExcelEntity(name = "民族")
    @ApiModelProperty("民族")
    @TableField(exist = false)
    private Nation nation;

    @ExcelEntity(name = "政治面貌")
    @ApiModelProperty("政治面貌")
    @TableField(exist = false)
    private PoliticsStatus politicsStatus;

    @ExcelEntity(name = "部门")
    @ApiModelProperty("部门")
    @TableField(exist = false)
    private Department department;

    @ExcelEntity(name = "职称")
    @ApiModelProperty("职称")
    @TableField(exist = false)
    private Joblevel joblevel;

    @ExcelEntity(name = "职位")
    @ApiModelProperty("职位")
    @TableField(exist = false)
    private Position position;

    @ApiModelProperty("员工薪资")
    @TableField(exist = false)
    private Salary salary;
}
