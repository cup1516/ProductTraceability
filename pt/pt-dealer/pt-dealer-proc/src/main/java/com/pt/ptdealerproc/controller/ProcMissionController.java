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
import com.pt.ptdealerproc.dto.MissionDto;
import com.pt.ptdealerproc.service.ProcMissionService;
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
@RequestMapping("/mission" )
@Api(value = "mission", tags = "个人任务管理")
public class ProcMissionController {

    private final ProcMissionService procMissionService;

    /**
     * 分页查询
     * @param page 分页对象
     * @return
            */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProcProcessPage(Page page, MissionDto missionDto) {
        return R.ok(procMissionService.getMissionPage(page,missionDto,SecurityUtils.getCompanyId()));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody MissionDto missionDto)
    {
        return R.ok(procMissionService.updateMissionStatus(missionDto,SecurityUtils.getCompanyId()));
    }

}
