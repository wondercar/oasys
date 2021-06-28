package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.pojo.Salary;
import com.hellowy.oasys.server.service.SalaryService;
import com.hellowy.oasys.server.vo.SalaryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 工资controller
 *
 * @author wondercar
 * @date 2021/2/24
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Resource
    private SalaryService salaryService;
    @ApiOperation("获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation("获取工资账套(分页)")
    @GetMapping("/")
    public DataGridView<Salary> getSalary(SalaryVO salaryVo) {
        return salaryService.getSalary(salaryVo);
    }

    @ApiOperation("添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(new Date());
        if (salaryService.save(salary)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation("修改工资账套")
    @PutMapping("/")
    public RespBean editSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }


}
