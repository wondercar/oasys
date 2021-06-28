package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.EmployeeEc;
import lombok.Data;

/**
 * 员工奖惩对象VO
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Data
public class EmployeeEcVO extends EmployeeEc {
    private Integer page;
    private Integer size;
}
