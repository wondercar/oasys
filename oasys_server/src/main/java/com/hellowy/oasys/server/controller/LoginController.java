package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.Admin;
import com.hellowy.oasys.server.vo.AdminLoginVO;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 用户登录controller
 *
 * @author wondercar
 * @date 2021/1/31
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Resource
    private AdminService adminService;

    /**
     * 用户登录
     *
     * @param adminLoginVO
     * @param request
     * @return
     */
    //@LogStar("登录")
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginVO adminLoginVO, HttpServletRequest request) {
        return adminService.login(adminLoginVO, request);
    }

    //@LogStar("获取当前登录的用户信息")
    @ApiOperation(value = "获取当前登录的用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        //获取当前登录的用户名
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        // 根据
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }

    //@LogStar("注销")
    @ApiOperation(value = "用户退出")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功！");
    }
}
