package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.MenuMapper;
import com.hellowy.oasys.server.pojo.Admin;
import com.hellowy.oasys.server.pojo.Menu;
import com.hellowy.oasys.server.service.MenuService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单service
 *
 * @author wondercar
 * @date 2021/2/1
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MenuMapper menuMapper;

    /**
     * 通过用户id查询菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        //拿到security上下文中当前登录的用户信息
        Integer principalId = ((Admin) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getId();
        //数据存入redis
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>) operations.get("menu_" + principalId);
        //如果集合的数据为空，则去数据库查询
        if (Collections.isEmpty(menus)) {
            menus = menuMapper.getMenuByAdminId(principalId);
            //将数据存入redis
            operations.set("menu_" + principalId, menus);
        }
        //根据用户id查询该用户对应的菜单列表
        return menus;
    }

    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return super.baseMapper.getMenusWithRole();
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return super.baseMapper.getAllMenus();
    }
}
