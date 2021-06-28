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
 * 员工培训类
 *
 * @author: WondercarWy
 * @since: 2021-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee_train")
@ApiModel(value = "员工培训对象", description = "")
public class EmployeeTrain implements Serializable {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("员工id")
    private Integer eid;
    @ApiModelProperty("培训时间")
    private Date trainDate;
    @ApiModelProperty("培训内容")
    private String trainContent;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("员工实体对象")
    @TableField(exist = false)
    private Employee employee;
    @ApiModelProperty("员工任职部门")
    @TableField(exist = false)
    private Department department;
}
