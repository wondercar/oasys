package com.hellowy.oasys.server.controller;

import com.hellowy.oasys.server.pojo.Nation;
import com.hellowy.oasys.server.service.NationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 民族controller
 *
 * @author wondercar
 * @date 2021/2/20
 */
@RestController
@RequestMapping("/employee/basic")
public class NationController {
    @Resource
    private NationService nationService;

    @ApiOperation("获取所有民族信息")
    @GetMapping("/getAllNation")
    public List<Nation> getAllNation() {
        return nationService.list();
    }
}
