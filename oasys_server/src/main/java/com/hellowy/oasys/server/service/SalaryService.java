package com.hellowy.oasys.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.Salary;
import com.hellowy.oasys.server.vo.SalaryVO;

/**
 * 工资service接口
 *
 * @author wondercar
 * @date 2021/2/24
 */
public interface SalaryService extends IService<Salary> {
    /**
     * 获取工资账套
     *
     * @param salaryVo
     * @return
     */
    DataGridView<Salary> getSalary(SalaryVO salaryVo);
}
