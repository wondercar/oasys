package com.hellowy.oasys.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.pojo.Role;

import java.util.List;

/**
 * 角色service接口
 *
 * @author wondercar
 * @date 2021/2/3
 */
public interface RoleService extends IService<Role> {
    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);

    /**
     * 根据角色id查询菜单id集合
     *
     * @param rid
     * @return
     */
    List<Integer> getMidByRid(Integer rid);
}
