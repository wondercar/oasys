package com.hellowy.oasys.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hellowy.oasys.server.config.constant.RoleConst;
import com.hellowy.oasys.server.pojo.*;
import com.hellowy.oasys.server.service.MenuService;
import com.hellowy.oasys.server.service.RoleService;
import com.hellowy.oasys.server.vo.RoleVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 权限组controller
 *
 * @author wondercar
 * @date 2021/2/3
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "获取所有角色(分页)")
    @GetMapping("/")
    public DataGridView getAllRoles(RoleVO roleVo) {
        //分页对象
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getSize());
        //查询条件
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like(!ObjectUtils.isEmpty(roleVo.getName()), "name", roleVo.getName());
        wrapper.like(!ObjectUtils.isEmpty(roleVo.getCode()), "code", roleVo.getCode());
        wrapper.orderByDesc("gmt_create");
        roleService.page(page, wrapper);
        return new DataGridView<Role>(page.getTotal(), page.getRecords());
    }

    @ApiOperation("添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (ObjectUtils.isEmpty(role)) {
            return RespBean.error("提交的数据为空！");
        }
        //添加"ROLE_"作为角色码的前缀
        if (!role.getCode().startsWith(RoleConst.ROLE_PREFIX)) {
            role.setCode(RoleConst.ROLE_PREFIX + role.getCode());
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name", role.getName());
        if (!ObjectUtils.isEmpty(roleService.getOne(wrapper))) {
            return RespBean.error("该角色名称已存在！");
        }
        wrapper = new QueryWrapper<>();
        wrapper.eq("code", role.getCode());
        if (!ObjectUtils.isEmpty(roleService.getOne(wrapper))) {
            return RespBean.error("该角色编码已存在！");
        }
        if (roleService.save(role)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/")
    public RespBean updateRole(@RequestBody Role role) {
        if (ObjectUtils.isEmpty(role)) {
            return RespBean.error("提交的数据为空！");
        }
        //判断角色信息是否已经存在
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name", role.getName());
        Role selectRole = roleService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(selectRole) && !role.getId().equals(selectRole.getId())) {
            return RespBean.error("该角色名称已存在");
        }
        wrapper = new QueryWrapper<>();
        wrapper.eq("code", role.getCode());
        selectRole = roleService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(selectRole) && !role.getId().equals(selectRole.getId())) {
            return RespBean.error("该角色编码已存在");
        }
        //添加"ROLE_"作为角色码的前缀
        if (!role.getCode().startsWith(RoleConst.ROLE_PREFIX)) {
            role.setCode(RoleConst.ROLE_PREFIX + role.getCode());
        }
        if (roleService.updateById(role)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public RespBean deleteRole(@PathVariable("id") Integer id) {
        if (roleService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/")
    public RespBean batchDeleteRole(Integer[] ids) {
        if (roleService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("批量删除成功！");
        }
        return RespBean.error("批量删除失败！");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/menuRole/{rid}")
    public List<Integer> getMidByRid(@PathVariable("rid") Integer rid) {
        return roleService.getMidByRid(rid);
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/menuRole")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return roleService.updateMenuRole(rid, mids);
    }

}
