package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.Admin;
import com.hellowy.oasys.server.service.AdminService;
import com.hellowy.oasys.server.vo.AdminVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wondercar
 * @date 2021/3/28
 * 在线聊天控制器
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private AdminService adminService;

    @ApiOperation("获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAdmins(String keywords){
        AdminVO adminVo = new AdminVO();
        adminVo.setPage(1);
        adminVo.setSize(1000);
        return adminService.getAllAdmins(adminVo).getRecords();
    }

}
