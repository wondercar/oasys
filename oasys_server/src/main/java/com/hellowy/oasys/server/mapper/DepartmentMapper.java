package com.hellowy.oasys.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellowy.oasys.server.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门mapper
 *
 * @author wondercar
 * @date 2021/2/8
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    /**
     * 递归查询所有部门
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 判断该部门下是否有子部门
     *
     * @param id
     * @return
     */
    Integer findContainChildren(Integer id);

    /**
     * 修改该部门的父部门
     *
     * @param id
     * @param parentId
     * @return
     */
    boolean editParentId(@Param("id") Integer id, @Param("parentId") Integer parentId);
}
