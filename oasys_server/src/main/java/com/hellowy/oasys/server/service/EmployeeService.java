package com.hellowy.oasys.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.vo.*;

import java.util.List;

/**
 * 员工service接口
 *
 * @author wondercar
 * @date 2021/2/8
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 根据条件查询员工（分页）
     *
     * @param employeeVo
     * @return
     */
    DataGridView<Employee> getAllEmployees(EmployeeVO employeeVo);

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    RespBean addEmployee(Employee employee);

    /**
     * 修改员工
     *
     * @param entity
     * @return
     */
    RespBean editEmployee(Employee entity);


    /**
     * 根据id查询详细员工详细
     *
     * @param id
     * @return
     */
    List<Employee> getEmployeeById(Integer id);

    /**
     * 获取员工账套
     *
     * @param name
     * @param workId
     * @param page
     * @param size
     * @return
     */
    DataGridView<Employee> getEmployeeWithSalary(String name, String workId, Integer page, Integer size);

    /**
     * 查询全部员工奖惩信息(分页)
     *
     * @param vo
     * @return
     */
    DataGridView<EmployeeEc> getAllEmployeeEc(EmployeeEcVO vo);
    /**
     * 查询全部员工培训记录
     *
     * @param vo
     * @return
     */
    DataGridView<EmployeeTrain> getAllEmployeeTrain(EmployeeTrainVO vo);
    /**
     * 查询全部员工调整记录
     *
     * @param vo
     * @return
     */
    DataGridView<EmployeeAdj> getAllEmployeeAdj(EmployeeAdjVO vo);
}
