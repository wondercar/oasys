package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.JoblevelMapper;
import com.hellowy.oasys.server.pojo.Joblevel;
import com.hellowy.oasys.server.service.JoblevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 职称service
 *
 * @author wondercar
 * @date 2021/2/3
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements JoblevelService {
}
