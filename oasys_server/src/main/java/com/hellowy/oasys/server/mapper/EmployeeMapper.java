package com.hellowy.oasys.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * 员工mapper
 *
 * @author wondercar
 * @date 2021/2/8
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据条件查询员工（分页）
     *
     * @param page
     * @param employeeVo
     * @return
     */
    IPage<Employee> getAllEmployees(IPage<Employee> page, @Param("employeeVo") EmployeeVO employeeVo);

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
     * @param page
     * @param name
     * @param workId
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(IPage page, @Param("name") String name, @Param("workId") String workId);

    /**
     * 根据salaryId删除员工信息
     *
     * @param id
     * @return
     */
    Integer removeBySaiaryId(Serializable id);

    /**
     * 分页查询全部员工奖惩信息
     * @return
     */
    IPage<EmployeeEc> getAllEmployeeEc(IPage<EmployeeEc> page, @Param("vo") EmployeeEcVO vo);
    /**
     * 分页查询全部员工培训信息
     * @return
     */
    IPage<EmployeeTrain> getAllEmployeeTrain(IPage<EmployeeTrain> page, @Param("vo") EmployeeTrainVO vo);
    /**
     * 分页查询全部员工调整信息
     * @return
     */
    IPage<EmployeeAdj> getAllEmployeeAdj(IPage<EmployeeAdj> page, @Param("vo") EmployeeAdjVO vo);
}
