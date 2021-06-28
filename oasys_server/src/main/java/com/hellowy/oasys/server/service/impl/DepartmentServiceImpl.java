package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.DepartmentMapper;
import com.hellowy.oasys.server.pojo.Department;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.service.DepartmentService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门service
 *
 * @author wondercar
 * @date 2021/2/8
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "deptData")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    //部门的根节点的父id
    private final Integer PARENT_ID = -1;

    /**
     * 查询所有部门
     *
     * @return
     */
    //加入缓存
    @Override
    @Cacheable(key = "#root.methodName")
    public List<Department> getAllDepartments() {
        return baseMapper.getAllDepartments(PARENT_ID);
    }

    /**
     * 判断该部门下是否有子部门
     *
     * @param id
     * @return
     */
    @Override
    public Integer findContainChildren(Integer id) {
        return baseMapper.findContainChildren(id);
    }

    /**
     * 执行添加
     *
     * @param entity
     * @return
     */
    @Override
    @CacheEvict(allEntries = true, condition = "#p0 != null")
    public boolean save(Department entity) {
        return super.save(entity);
    }

    /**
     * 执行修改
     *
     * @param entity
     * @return
     */
    @Override
    @CacheEvict(allEntries = true, condition = "#p0 != null")
    public boolean updateById(Department entity) {
        return super.updateById(entity);
    }

    /**
     * 修改该部门的父部门
     *
     * @param id
     * @param parentId
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    public boolean editParentId(Integer id, Integer parentId) {
        return baseMapper.editParentId(id, parentId);
    }

    /**
     * 执行删除
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(allEntries = true, condition = "#p0 != null && #id > 0")
    public RespBean removeById(Integer id) {
        //执行删除
        return super.removeById(id)
                ? RespBean.success("删除成功！")
                : RespBean.error("删除失败！");
    }

}
