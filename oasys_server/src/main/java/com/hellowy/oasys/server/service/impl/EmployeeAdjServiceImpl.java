package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.EmployeeAdjMapper;
import com.hellowy.oasys.server.pojo.EmployeeAdj;
import com.hellowy.oasys.server.service.EmployeeAdjService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工调整service
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeAdjServiceImpl extends ServiceImpl<EmployeeAdjMapper, EmployeeAdj> implements EmployeeAdjService {
}
