package com.hellowy.oasys.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.vo.AdminLoginVO;
import com.hellowy.oasys.server.vo.AdminVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 用户service接口
 *
 * @author wondercar
 * @date 2021/1/26
 */
public interface AdminService extends IService<Admin> {
    /**
     * 用户登录之后返回token
     *
     * @param adminLoginVO
     * @param request
     * @return
     */
    RespBean login(AdminLoginVO adminLoginVO, HttpServletRequest request);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

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
     * @return
     */
    IPage<Admin> getAllAdmins(AdminVO adminVo);

    /**
     * 更新操作员角色
     *
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * 更新用户密码
     *
     * @param oldPassword
     * @param password
     * @param adminId
     * @return
     */
    RespBean updateAdminPassword(String oldPassword, String password, Integer adminId);
}
