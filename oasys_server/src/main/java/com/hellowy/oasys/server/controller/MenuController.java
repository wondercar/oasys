package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.Menu;
import com.hellowy.oasys.server.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单controller
 *
 * @author wondercar
 * @date 2021/2/1
 */
@RestController
@RequestMapping("/system/config")
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId() {
        return menuService.getMenuByAdminId();
    }
}
