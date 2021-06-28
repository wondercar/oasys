package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.EmployeeTrain;
import lombok.Data;

/**
 * 工资调整VO
 *
 * @author: WondercarWy
 * @since: 2021-03-05
 */
@Data
public class EmployeeTrainVO extends EmployeeTrain {
    private Integer page;
    private Integer size;
}
