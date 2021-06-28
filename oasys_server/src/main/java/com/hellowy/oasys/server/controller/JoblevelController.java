package com.hellowy.oasys.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hellowy.oasys.server.pojo.DataGridView;
import com.hellowy.oasys.server.pojo.Joblevel;
import com.hellowy.oasys.server.pojo.RespBean;
import com.hellowy.oasys.server.service.JoblevelService;
import com.hellowy.oasys.server.vo.JoblevelVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 职称管理controller
 *
 * @author wondercar
 * @date 2021/2/3
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Resource
    private JoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称(分页)")
    @GetMapping("/")
    public DataGridView<Joblevel> getAllJoblevels(JoblevelVO joblevelVo) {
        IPage<Joblevel> page = new Page<>(joblevelVo.getPage(), joblevelVo.getSize());
        //查询条件
        QueryWrapper<Joblevel> wrapper = new QueryWrapper<>();
        wrapper.like(!ObjectUtils.isEmpty(joblevelVo.getName()), "name", joblevelVo.getName());
        wrapper.eq(!ObjectUtils.isEmpty(joblevelVo.getTitleLevel()), "title_level", joblevelVo.getTitleLevel());
        wrapper.orderByDesc("create_date");
        joblevelService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/getAllJoblevels")
    public List<Joblevel> getAllSimpleJoblevels() {
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "修改职称")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJoblevel(@PathVariable("id") Integer id) {
        if (joblevelService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public RespBean batchDeleteJoblevel(Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("批量删除成功！");
        }
        return RespBean.error("批量删除失败！");
    }

}
