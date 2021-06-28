package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.RoleMapper;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.pojo.Role;
import com.hellowy.oasys.server.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author wondercar
 * @date 2021/2/3 - 20:50
 * 权限组业务层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //根据角色id删除该角色对应的所有菜单
        super.baseMapper.removeMenuByRids(Arrays.asList(rid));
        //执行更新
        if(ObjectUtils.isEmpty(mids) ||
                super.baseMapper.updateMenuRole(rid,mids).equals(mids.length)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 根据角色id查询菜单id集合
     *
     * @param rid
     * @return
     */
    @Override
    public List<Integer> getMidByRid(Integer rid) {
        return super.baseMapper.getMidByRid(rid);
    }

    /**
     * 删除角色及该角色所属角色菜单表中的数据
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //删除该角色所属角色菜单表中的数据
        super.baseMapper.removeMenuByRids(Arrays.asList(id));
        super.baseMapper.removeAdminByRids(Arrays.asList(id));
        //删除该角色
        return super.removeById(id);
    }

    /**
     * 批量删除角色
     * @param idList
     * @return
     */
    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        //删除关系表用户角色数据
        super.baseMapper.removeAdminByRids(idList);
        //删除关系表角色菜单数据
        super.baseMapper.removeMenuByRids(idList);
        return super.removeByIds(idList);
    }
}
