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

package com.pt.ptdealerprod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerprod.entity.ProdType;
import com.pt.ptdealerprod.service.ProdTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * @author wl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/type" )
@Api(value = "type", tags = "类型表管理")
public class ProdTypeController {

    private ProdTypeService prodTypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param prodType 加工类型表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProdTypePage(Page page, ProdType prodType) {
        return R.ok(prodTypeService.getProdTypePage(page, prodType,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取类型列表
     * @return
     */
    @GetMapping("/list" )
    public R getProdTypeList() {
        return R.ok(prodTypeService.getProdTypeList(SecurityUtils.getCompanyId()));
    }

    /**
     * 获取类型列表
     * @return
     */
    @GetMapping("/list/{typeName}" )
    public R getProdTypeListByName(@PathVariable String typeName) {
        return R.ok(prodTypeService.getProdTypeListByName(SecurityUtils.getCompanyId(),typeName));
    }
    /**
     * 根据类型编号获取详细信息
     */
    @GetMapping(value = "/{typeId}")
    public R getInfo(@PathVariable String typeId)
    {
        return R.ok(prodTypeService.selectTypeById(typeId,SecurityUtils.getCompanyId()));
    }

    /**
     * 新增类型
     */

    @PostMapping
    public R add( @RequestBody ProdType type)
    {
        prodTypeService.checkTypeNameUnique(type,SecurityUtils.getCompanyId());
        prodTypeService.checkTypeCodeUnique(type,SecurityUtils.getCompanyId());
        type.setCreateBy(SecurityUtils.getUserName());
        return R.ok(prodTypeService.insertType(type,SecurityUtils.getCompanyId()));
    }

    /**
     * 修改类型
     */

    @PutMapping
    public R edit(@Validated @RequestBody ProdType type)
    {
        prodTypeService.checkTypeNameUnique(type,SecurityUtils.getCompanyId());
        prodTypeService.checkTypeCodeUnique(type,SecurityUtils.getCompanyId());
        return R.ok(prodTypeService.updateType(type,SecurityUtils.getCompanyId()));
    }

    /**
     * 删除类型
     */

    @DeleteMapping("/{typeIds}")
    public R remove(@PathVariable String[] typeIds)
    {
        return R.ok(prodTypeService.deleteTypeByIds(typeIds,SecurityUtils.getCompanyId()));
    }

    /**
     * 获取类型选择框列表
     */
    @GetMapping("/optionselect")
    public R optionselect()
    {
        List<ProdType> types = prodTypeService.selectTypeAll(SecurityUtils.getCompanyId());
        return R.ok(types);
    }

}
