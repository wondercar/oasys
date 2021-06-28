package com.hellowy.oasys.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 工资实体类
 *
 * @author wondercar
 * @date 2021/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_salary")
@ApiModel(value = "salary对象", description = "")
public class Salary implements Serializable {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("基本工资")
    private Double basicSalary;

    @ApiModelProperty("奖金")
    private Double bonus;

    @ApiModelProperty("午餐补助")
    private Double lunchSalary;

    @ApiModelProperty("交通补助")
    private Double trafficSalary;

    @ApiModelProperty("养老基金数")
    private Double pensionBase;

    @ApiModelProperty("养老金比率")
    private Float pensionPer;

    @ApiModelProperty("医疗基数")
    private Integer medicalBase;

    @ApiModelProperty("医疗保险比率")
    private Float medicalPer;

    @ApiModelProperty("公积金基数")
    private Integer accumulationFundBase;

    @ApiModelProperty("公积金比率")
    private Float accumulationFundPer;

    @ApiModelProperty("应发工资")
    private Double allSalary;

    @ApiModelProperty("启用时间")
    private Date createDate;
}
