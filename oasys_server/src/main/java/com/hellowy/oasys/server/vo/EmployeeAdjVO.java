package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.EmployeeAdj;
import lombok.Data;

/**
 * 员工调整VO
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Data
public class EmployeeAdjVO extends EmployeeAdj {
    private Integer page;
    private Integer size;
}