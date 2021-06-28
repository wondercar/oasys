package com.hellowy.oasys.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.Employee;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.pojo.Salary;
import com.hellowy.oasys.server.service.EmployeeService;
import com.hellowy.oasys.server.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工账套controller
 *
 * @author wondercar
 * @date 2021/2/24
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {
    @Resource
    private EmployeeService employeeService;

    @ApiOperation("获取所有员工账套")
    @GetMapping("/")
    public DataGridView<Employee> getEmployeeWithSalary(String name, String workId, @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeWithSalary(name, workId, page, size);
    }

    @ApiOperation("更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid) {
        UpdateWrapper<Employee> wrapper = new UpdateWrapper<>();
        wrapper.set("salary_id", sid).eq("id", eid);
        if (employeeService.update(wrapper)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
