package com.hellowy.oasys.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户奖惩实体类
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee_ec")
@ApiModel(value = "员工奖惩对象", description = "")
public class EmployeeEc extends Employee implements Serializable {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("员工id")
    private Integer eid;
    @ApiModelProperty("奖惩时间")
    private Date ecDate;
    @ApiModelProperty("培训内容")
    private String ecReason;
    @ApiModelProperty("分数")
    private Integer ecPoint;
    @ApiModelProperty("奖惩类型")
    private Integer ecType;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("员工实体对象")
    @TableField(exist = false)
    private Employee employee;
    @ApiModelProperty("员工任职部门")
    @TableField(exist = false)
    private Department department;
}