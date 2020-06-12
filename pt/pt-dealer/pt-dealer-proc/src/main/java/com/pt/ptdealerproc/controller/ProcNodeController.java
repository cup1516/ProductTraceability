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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommon.util.R;
import com.pt.ptdealerproc.entity.ProcNode;
import com.pt.ptdealerproc.service.ProcNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 加工节点表
 *
 * @author pig code generator
 * @date 2020-04-19 10:46:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/node" )
@Api(value = "node", tags = "加工节点表管理")
public class ProcNodeController {

    private final ProcNodeService procNodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param proceNode 加工节点表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProceNodePage(Page page, ProcNode proceNode) {
        return R.ok(procNodeService.page(page, Wrappers.query(proceNode)));
    }

	/**
	 * 获取角色列表
	 *
	 * @return 角色列表
	 */
	@GetMapping("/list")
	public R listNodes() {
		return R.ok(procNodeService.list(Wrappers.emptyWrapper()));
	}


    /**
     * 通过id查询加工节点表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(procNodeService.getById(id));
    }

    /**
     * 新增加工节点表
     * @param proceNode 加工节点表
     * @return R
     */
    @ApiOperation(value = "新增加工节点表", notes = "新增加工节点表")
    @PostMapping
    public R save(@RequestBody ProcNode proceNode) {
        return R.ok(procNodeService.saveNode(proceNode));
    }

    /**
     * 修改加工节点表
     * @param proceNode 加工节点表
     * @return R
     */
    @ApiOperation(value = "修改加工节点表", notes = "修改加工节点表")
    @PutMapping
    public R updateById(@RequestBody ProcNode proceNode) {
        return R.ok(procNodeService.updateById(proceNode));
    }

    /**
     * 通过id删除加工节点表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除加工节点表", notes = "通过id删除加工节点表")
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(procNodeService.removeById(id));
    }

}
