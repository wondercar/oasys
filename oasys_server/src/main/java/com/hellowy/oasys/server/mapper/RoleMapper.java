package com.hellowy.oasys.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellowy.oasys.server.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色mapper
 *
 * @author wondercar
 * @date 2021/2/3
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @return
     */
    Integer updateMenuRole(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

    /**
     * 根据角色id查询菜单id集合
     *
     * @param rid
     * @return
     */
    List<Integer> getMidByRid(Integer rid);

    /**
     * 批量删除角色菜单表中的角色信息
     *
     * @param idList
     */
    Integer removeMenuByRids(Collection<? extends Serializable> idList);

    /**
     * 批量删除用户角色表中的角色信息
     *
     * @param idList
     */
    Integer removeAdminByRids(Collection<? extends Serializable> idList);
}
