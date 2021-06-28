package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.Employee;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 业务层员工实体类
 *
 * @author wondercar
 * @date 2021/2/14
 */
@Data
public class EmployeeVO extends Employee {
    private Integer page;
    private Integer size;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date[] beginDateScope;
}
