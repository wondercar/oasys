package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.Salary;
import lombok.Data;

/**
 * 业务层工资实体类
 *
 * @author wondercar
 * @date 2021/2/24
 */
@Data
public class SalaryVO extends Salary {
    private Integer page;
    private Integer size;
}
