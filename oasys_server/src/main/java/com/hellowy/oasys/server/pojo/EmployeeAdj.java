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
 * 员工调动实体类
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee_move")
@ApiModel(value = "员工调整对象", description = "")
public class EmployeeAdj implements Serializable {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("员工对象")
    private  Employee employee;
    @ApiModelProperty("调整前部门对象")
    private Department beforeDep;
    @ApiModelProperty("调整后部门对象")
    private Department afterDep;
    @ApiModelProperty("调整前职务对象")
    private Position beforePos;
    @ApiModelProperty("调整后职务对象")
    private Position afterPos;
    @ApiModelProperty("调整前工资对象")
    private Salary beforeSalary;
    @ApiModelProperty("调整后工资对象")
    private Salary afterSalary;
    @ApiModelProperty("调整时间")
    private Date removeDate;
    @ApiModelProperty("调整原因")
    private String reason;
    @ApiModelProperty("备注")
    private String remark;
}
