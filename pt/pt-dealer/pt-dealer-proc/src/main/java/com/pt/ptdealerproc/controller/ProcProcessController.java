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
import com.pt.ptcommon.util.R;
import com.pt.ptdealerproc.dto.MissionDto;
import com.pt.ptdealerproc.dto.ProcessDto;
import com.pt.ptdealerproc.service.ProcProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
        return R.ok(procProcessService.getProcessPage(page,processDto));
    }
	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param missionDto 加工流程表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/mission" )
	public R getPersonalMissionPage(Page page, MissionDto missionDto) {
		return R.ok(procProcessService.getMissionPage(page,missionDto));
	}
    /**
     * 通过id查询加工流程表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(procProcessService.getById(id));
    }

    /**
     * 新增加工流程表
     * @param
     * @return R
     */
    @ApiOperation(value = "新增加工流程表", notes = "新增加工流程表")
    @PostMapping
    public R save(@RequestBody ProcessDto processDto ) {
        return R.ok(procProcessService.saveProcess(processDto));
    }

    /**
     * 修改加工流程表
     * @param processDto 加工流程表
     * @return R
     */
    @ApiOperation(value = "修改加工流程表", notes = "修改加工流程表")
    @PutMapping
    public R updateById(@RequestBody ProcessDto processDto ) {
        return R.ok(procProcessService.updateProcessById(processDto));
}

    /**
     * 通过id删除加工流程表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除加工流程表", notes = "通过id删除加工流程表")
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(procProcessService.removeProcessById(id));
    }

}
