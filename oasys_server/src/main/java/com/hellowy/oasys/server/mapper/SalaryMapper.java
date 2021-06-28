package com.hellowy.oasys.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellowy.oasys.server.pojo.Salary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工资mapper
 *
 * @author wondercar
 * @date 2021/2/24
 */
@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {
}
