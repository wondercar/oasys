package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.EmployeeEcMapper;
import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.EmployeeEc;
import com.hellowy.oasys.server.service.EmployeeEcService;
import com.hellowy.oasys.server.vo.EmployeeEcVO;
import org.springframework.stereotype.Service;

/**
 * 员工奖惩service
 *
 * @author: WondercarWy
 * @since: 2021-05-05
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements EmployeeEcService {
}
