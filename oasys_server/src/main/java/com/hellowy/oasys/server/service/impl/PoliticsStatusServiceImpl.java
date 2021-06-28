package com.hellowy.oasys.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellowy.oasys.server.mapper.PoliticsStatusMapper;
import com.hellowy.oasys.server.pojo.PoliticsStatus;
import com.hellowy.oasys.server.service.PoliticsStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 政治面貌service
 *
 * @author wondercar
 * @date 2021/2/20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PoliticsStatusServiceImpl extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements PoliticsStatusService {
}
