package com.hellowy.oasys.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hellowy.oasys.server.pojo.Menu;

import java.util.List;

/**
 * 菜单service接口
 *
 * @author wondercar
 * @date 2021/2/1
 */
public interface MenuService extends IService<Menu> {
    /**
     * 通过用户id查询菜单列表
     *
     * @return
     */
    List<Menu> getMenuByAdminId();

    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     *
     * @return
     */
    List<Menu> getAllMenus();
}
