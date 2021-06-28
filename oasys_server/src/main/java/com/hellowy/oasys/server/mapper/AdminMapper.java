package com.hellowy.oasys.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hellowy.oasys.server.pojo.Admin;
import com.hellowy.oasys.server.pojo.Role;
import com.hellowy.oasys.server.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;


/**
 * 用户mapper
 *
 * @author wondercar
 * @date 2021/1/26
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @return
     */
    Set<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员(不包括当前登录的)
     *
     * @param adminVo
     */
    IPage<Admin> getAllAdminExCurrent(IPage page, @Param("adminVo") AdminVO adminVo);

    /**
     * 删除操作员及操作员角色关系表中的数据
     *
     * @param id
     */
    Integer removeRoleByAid(Serializable id);

    /**
     * 更新操作员角色
     *
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
