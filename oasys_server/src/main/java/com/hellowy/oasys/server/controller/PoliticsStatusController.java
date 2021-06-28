package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.PoliticsStatus;
import com.hellowy.oasys.server.service.PoliticsStatusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 政治面貌controller
 *
 * @author wondercar
 * @date 2021/2/20
 */
@RestController
@RequestMapping("/employee/basic")
public class PoliticsStatusController {
    @Resource
    private PoliticsStatusService politicsStatusService;

    @ApiOperation("获取所有政治面貌信息")
    @GetMapping("/getAllPoliticsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }
}
