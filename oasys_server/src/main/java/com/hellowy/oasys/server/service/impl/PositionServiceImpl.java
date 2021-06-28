package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.PositionMapper;
import com.hellowy.oasys.server.pojo.Position;
import com.hellowy.oasys.server.service.PositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
}
