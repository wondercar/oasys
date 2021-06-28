package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.EmployeeTrainMapper;
import com.hellowy.oasys.server.pojo.EmployeeTrain;
import com.hellowy.oasys.server.service.EmployeeTrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工培训service
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeTrainServiceImpl extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain> implements EmployeeTrainService {
}
