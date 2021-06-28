package com.hellowy.oasys.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hellowy.oasys.server.pojo.Admin;
import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.pojo.Role;
import com.hellowy.oasys.server.service.AdminService;
import com.hellowy.oasys.server.service.RoleService;
import com.hellowy.oasys.server.vo.AdminVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户controller
 *
 * @author wondercar
 * @date 2021/2/9
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;
    @Resource
    private BCryptPasswordEncoder encoder;

    @ApiOperation("获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation("获取所有操作员(不包括当前登录的操作员，分页)")
    @GetMapping("/")
    public DataGridView<Admin> getAllAdmins(AdminVO adminVo) {
        IPage<Admin> allAdmins = adminService.getAllAdmins(adminVo);
        return new DataGridView<>(allAdmins.getTotal(), allAdmins.getRecords());
    }

    @ApiOperation("修改操作员")
    @PutMapping("/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        // 执行数据库修改
        if (adminService.updateById(admin)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation("修改操作员启用状态")
    @PutMapping("/")
    public RespBean updateAdminStatus(Integer id, Boolean enabled) {
        Admin admin = new Admin();
        admin.setId(id);
        // 决定账户是否启用
        admin.setEnabled(enabled);
        // 执行数据库修改
        if (adminService.updateById(admin)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改错误！");
    }

    @ApiOperation("修改操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer id, Integer[] rids) {
        return adminService.updateAdminRole(id, rids);
    }

    @ApiOperation("添加操作员")
    @PostMapping("/")
    public RespBean addAdmin(@RequestBody Admin admin){
        // 对用户密码加密
        admin.setPassword(encoder.encode(admin.getPassword()));
        // 执行数据库新增
        if(adminService.save(admin)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        // 执行数据库删除
        if (adminService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
