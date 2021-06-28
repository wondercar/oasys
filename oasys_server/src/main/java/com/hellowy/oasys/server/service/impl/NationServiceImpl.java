package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.NationMapper;
import com.hellowy.oasys.server.pojo.Nation;
import com.hellowy.oasys.server.service.NationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 民族service
 *
 * @author wondercar
 * @date 2021/2/20 - 22:12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

}
