package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.EmployeeMapper;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.service.EmployeeService;
import com.hellowy.oasys.server.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * 员工service
 *
 * @author wondercar
 * @date 2021/2/8
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    /**
     * 根据条件查询员工（分页）
     *
     * @param employeeVo
     * @return
     */
    @Override
    public DataGridView<Employee> getAllEmployees(EmployeeVO employeeVo) {
        //开启分页
        IPage<Employee> page = new Page<>(employeeVo.getPage(), employeeVo.getSize());
        page = baseMapper.getAllEmployees(page, employeeVo);
        return new DataGridView<>(page.getTotal(), page.getRecords());
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @Override
    public RespBean addEmployee(Employee employee) {
        if (ObjectUtils.isEmpty(employee)) {
            return RespBean.error("提交数据为空！");
        }
        if (ObjectUtils.isEmpty(employee.getSchool())) {
            employee.setSchool("无");
        }
        if (ObjectUtils.isEmpty(employee.getSpecialty())) {
            employee.setSpecialty("无");
        }
        if (!ObjectUtils.isEmpty(super.getOne(
                new QueryWrapper<Employee>().eq("work_id", employee.getWorkId())))) {
            return RespBean.error("该工号已存在！");
        }
        //处理合同期限
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        try {
            //合同截止日期减去合同起始日期
            employee.setContractTerm((double) ((endContract.getTime() - beginContract.getTime()) / (24 * 60 * 60 * 1000)));
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("日期类型处理发生异常!");
        }
        //执行数据库新增操作
        if (super.save(employee)) {
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    /**
     * 修改员工
     *
     * @param entity
     * @return
     */
    @Override
    public RespBean editEmployee(Employee entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return RespBean.error("提交数据为空！");
        }
        //处理合同期限
        Date beginContract = entity.getBeginContract();
        Date endContract = entity.getEndContract();
        try {
            entity.setContractTerm((double) ((endContract.getTime() - beginContract.getTime()) / (24 * 60 * 60 * 1000)));
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("日期类型处理发生异常!");
        }
        if (super.updateById(entity)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    /**
     * 根据id查询详细员工详细
     *
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployeeById(Integer id) {
        return baseMapper.getEmployeeById(id);
    }

    /**
     * 获取员工账套
     *
     * @param name
     * @param workId
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public DataGridView<Employee> getEmployeeWithSalary(String name, String workId, Integer currentPage, Integer size) {
        IPage<Employee> page = new Page<>(currentPage, size);
        page = baseMapper.getEmployeeWithSalary(page, name, workId);
        return new DataGridView<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询全部员工奖惩信息
     *
     * @param employeeEcVO
     * @return
     */
    @Override
    public DataGridView<EmployeeEc> getAllEmployeeEc(EmployeeEcVO employeeEcVO) {
        //开启分页
        IPage<EmployeeEc> page = new Page<>(employeeEcVO.getPage(), employeeEcVO.getSize());
        page = baseMapper.getAllEmployeeEc(page, employeeEcVO);
        return new DataGridView<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询全部员工培训信息
     *
     * @param employeeTrainVO
     * @return
     */
    @Override
    public DataGridView<EmployeeTrain> getAllEmployeeTrain(EmployeeTrainVO employeeTrainVO) {
        //开启分页
        IPage<EmployeeTrain> page = new Page<>(employeeTrainVO.getPage(), employeeTrainVO.getSize());
        page = baseMapper.getAllEmployeeTrain(page, employeeTrainVO);
        return new DataGridView<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询全部员工调动信息
     *
     * @param employeeMoveVO
     * @return
     */
    @Override
    public DataGridView<EmployeeAdj> getAllEmployeeAdj(EmployeeAdjVO employeeMoveVO) {
        //开启分页
        IPage<EmployeeAdj> page = new Page<>(employeeMoveVO.getPage(), employeeMoveVO.getSize());
        page = baseMapper.getAllEmployeeAdj(page, employeeMoveVO);
        return new DataGridView<>(page.getTotal(), page.getRecords());
    }
}
