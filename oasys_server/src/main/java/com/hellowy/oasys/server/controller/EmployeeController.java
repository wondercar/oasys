package com.hellowy.oasys.server.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.service.*;
import com.hellowy.oasys.server.utils.IdUtil;
import com.hellowy.oasys.server.vo.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 员工controller
 *
 * @author wondercar
 * @date 2021/2/14
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private NationService nationService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private JoblevelService joblevelService;
    @Resource
    private PoliticsStatusService politicsStatusService;
    @Resource
    private PositionService positionService;
    @Resource
    private EmployeeEcService employeeEcService;
    @Resource
    private EmployeeTrainService employeeTrainService;
    @Resource
    private EmployeeAdjService employeeAdjService;

    @ApiOperation("获取所有员工（分页）")
    @GetMapping("/")
    public DataGridView<Employee> getAllEmployees(EmployeeVO employeeVo) {
        return employeeService.getAllEmployees(employeeVo);
    }

    @ApiOperation("获取工号")
    @GetMapping("/getWorkID")
    public String getWorkID() {
        // 利用uuid动态生成工号
        // 截取15位作为工号
        return IdUtil.getUUID().substring(0, 15).toUpperCase();
    }

    @ApiOperation("添加员工")
    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("修改员工")
    @PutMapping("/")
    public RespBean editEmployee(@RequestBody Employee employee) {
        return employeeService.editEmployee(employee);
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployee(@PathVariable("id") Integer id) {
        if (employeeService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation("获取全部员工奖惩信息")
    @GetMapping("/getAllEmpEc")
    public DataGridView<EmployeeEc> getEmpEc(EmployeeEcVO vo){
        DataGridView<EmployeeEc> result = employeeService.getAllEmployeeEc(vo);
        return result;
    }
    @ApiOperation("删除全部员工奖惩信息")
    @GetMapping("/delEmpEc")
    public RespBean delEmpEc(Integer id){
        if (employeeEcService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation("获取全部员工培训信息")
    @GetMapping("/getAllEmpTrain")
    public DataGridView<EmployeeTrain> getEmpTrain(EmployeeTrainVO employeeTrainVO){
        DataGridView<EmployeeTrain> result = employeeService.getAllEmployeeTrain(employeeTrainVO);
        return result;
    }
    @ApiOperation("删除员工培训信息")
    @GetMapping("/delEmpTrain")
    public RespBean delEmpTrain(Integer id){
        if (employeeTrainService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation("获取全部员工调动信息")
    @GetMapping("/getAllEmpAdj")
    public DataGridView<EmployeeAdj> getAllEmpAdj(EmployeeAdjVO vo){
        DataGridView<EmployeeAdj> result = employeeService.getAllEmployeeAdj(vo);
        return result;
    }
    @ApiOperation("删除员工调动信息")
    @GetMapping("/delEmpAdj")
    public RespBean delEmpAdj(Integer id){
        if (employeeAdjService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation("员工数据导出")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void employeeExport(HttpServletResponse response) {
        //查询所有员工详细详细
        List<Employee> employees = employeeService.getEmployeeById(null);
        ExportParams exportParams = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        //将员工数据转换为数据表格对象
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Employee.class, employees);
        ServletOutputStream outputStream = null;
        try {
            //以流形式导出
            response.setHeader("content-type", "application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" +
                    URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            //写出数据
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation("员工数据导入")
    @PostMapping("/import")
    public RespBean employeeImport(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        //去掉标题行防止报错
        importParams.setTitleRows(1);
        //获取所有民族
        List<Nation> nations = nationService.list();
        //获取所有部门
        List<Department> departments = departmentService.list();
        //获取所有职称
        List<Joblevel> joblevels = joblevelService.list();
        //获取所有职位
        List<Position> positions = positionService.list();
        //获取所有政治面貌
        List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
        try {
            List<Employee> employees = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
            employees.forEach(employee -> {
                //设置员工对象的民族id
                employee.setNationId(nations.get(
                        nations.indexOf(new Nation(employee.getNation().getName()))
                ).getId());
                //设置员工对象的部门id
                employee.setDepartmentId(departments.get(
                        departments.indexOf(new Department(employee.getDepartment().getName()))
                ).getId());
                //设置员工对象的职称id
                employee.setJobLevelId(joblevels.get(
                        joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))
                ).getId());
                //设置员工对象的政治面貌id
                employee.setPoliticId(politicsStatuses.get(
                        politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))
                ).getId());
                //设置员工对象的职位id
                employee.setPosId(positions.get(
                        positions.indexOf(new Position(employee.getPosition().getName()))
                ).getId());
            });
            if (employeeService.saveBatch(employees)) {
                return RespBean.success("导入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败！");
    }
}
