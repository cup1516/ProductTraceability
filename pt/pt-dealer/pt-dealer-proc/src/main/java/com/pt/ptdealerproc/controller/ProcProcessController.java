/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pt.ptdealerproc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerproc.dto.ProcessDto;
import com.pt.ptdealerproc.entity.ProcProcess;
import com.pt.ptdealerproc.service.ProcProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 加工流程表
 *
 * @author pig code generator
 * @date 2020-04-19 14:58:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/process" )
@Api(value = "process", tags = "加工流程表管理")
public class ProcProcessController {

    private final ProcProcessService procProcessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param processDto 加工流程表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProcProcessPage(Page page, ProcessDto processDto) {
        return R.ok(procProcessService.getProcessPage(page,processDto,SecurityUtils.getCompanyId()));
    }
    /**
     * 分页查询
     * @param page 分页对象
     * @param processDto 加工流程表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/check/page" )
    public R getProcProcessCheckPage(Page page, ProcessDto processDto) {
        return R.ok(procProcessService.getProcessCheckPage(page,processDto,SecurityUtils.getCompanyId()));
    }
    /**
     * 获取流程列表
     * @return
     */
    @GetMapping("/list" )
    public R getProcProcessList() {
        return R.ok(procProcessService.getProcProcessList(SecurityUtils.getCompanyId()));
    }

    /**
     * 根据流程编号获取详细信息
     */
    @GetMapping(value = "/{processId}")
    public R getInfo(@PathVariable String processId)
    {
        return R.ok(procProcessService.selectProcessById(processId,SecurityUtils.getCompanyId()));
    }

    /**
     * 新增流程
     */

    @PostMapping
    public R add( @RequestBody ProcessDto processDto)
    {
        procProcessService.checkProcessNameUnique(processDto,SecurityUtils.getCompanyId());
        procProcessService.checkProcessCodeUnique(processDto,SecurityUtils.getCompanyId());
        processDto.setCreateBy(SecurityUtils.getUserName());
        return R.ok(procProcessService.insertProcess(processDto,SecurityUtils.getCompanyId()));
    }

    /**
     * 修改流程
     */

    @PutMapping
    public R edit( @RequestBody ProcessDto processDto)
    {
        procProcessService.checkProcessNameUnique(processDto,SecurityUtils.getCompanyId());
        procProcessService.checkProcessCodeUnique(processDto,SecurityUtils.getCompanyId());
        return R.ok(procProcessService.updateProcess(processDto,SecurityUtils.getCompanyId()));
    }

    /**
     * 删除流程
     */

    @DeleteMapping("/{processIds}")
    public R remove(@PathVariable String[] processIds)
    {
        return R.ok(procProcessService.deleteProcessByIds(processIds,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取流程选择框列表
     */
    @GetMapping("/optionselect")
    public R optionselect()
    {
        List<ProcProcess> processs = procProcessService.selectProcessAll(SecurityUtils.getCompanyId());
        return R.ok(processs);
    }
    /**
     * 状态修改
     */
    @PutMapping("/changeCheckStatus")
    public R changeCheckStatus(@RequestBody ProcessDto processDto)
    {
        return R.ok(procProcessService.changeCheckStatus(processDto,SecurityUtils.getCompanyId()));
    }
}
