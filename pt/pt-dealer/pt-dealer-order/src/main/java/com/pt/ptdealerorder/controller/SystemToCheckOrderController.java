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

package com.pt.ptdealerorder.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerorder.entity.SystemOrder;
import com.pt.ptdealerorder.service.SystemToCheckOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author wl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tocheck" )
@Api(value = "tocheck")
public class SystemToCheckOrderController {

    private final SystemToCheckOrderService systemToCheckOrderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystemOrderPage(Page page, SystemOrder systemOrder) {
        return R.ok(systemToCheckOrderService.getSystemOrderPage(page, systemOrder,SecurityUtils.getId(),SecurityUtils.getCompanyId()));
    }



	@PutMapping("/changeCheckStatus" )
    public R changeCheckStatus(@RequestBody SystemOrder systemOrder){
    	return R.ok(systemToCheckOrderService.changeCheckStatus(systemOrder));
	}
}