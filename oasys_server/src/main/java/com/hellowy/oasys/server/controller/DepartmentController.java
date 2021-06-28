package com.hellowy.oasys.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hellowy.oasys.server.pojo.Department;
import com.hellowy.oasys.server.pojo.Employee;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.service.DepartmentService;
import com.hellowy.oasys.server.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 部门controller
 *
 * @author wondercar
 * @date 2021/2/8
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private EmployeeService employeeService;

    @ApiOperation("获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation("添加部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department dept) {
        dept.setCreateDate(new Date());
        if (departmentService.save(dept)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation("修改部门")
    @PutMapping("/")
    public RespBean editDepartment(@RequestBody Department department) {
        if (departmentService.updateById(department)) {
            return RespBean.success("修改成功！", department);
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public RespBean DeleteDepartment(@PathVariable("id") Integer id) {
        //判断该部门下是否有子部门
        if (0 < departmentService.findContainChildren(id)) {
            return RespBean.error("该部门下已有子部门，请先删除子部门！");
        }
        //根据部门id查询该部门下是否有员工
        Employee emp = employeeService.getOne(new QueryWrapper<Employee>().eq("department_id", id));
        if (!ObjectUtils.isEmpty(emp)) {
            return RespBean.error("该部门下已有员工，请先删除员工！");
        }
        return departmentService.removeById(id);
    }

    @ApiOperation("修改该部门的父部门")
    @PutMapping("/editParentId/")
    public RespBean editParentId(Integer id, Integer parentId) {
        if (departmentService.editParentId(id, parentId)) {
            return RespBean.success("修改成功!");
        }
        return RespBean.error("修改失败!");
    }

}
