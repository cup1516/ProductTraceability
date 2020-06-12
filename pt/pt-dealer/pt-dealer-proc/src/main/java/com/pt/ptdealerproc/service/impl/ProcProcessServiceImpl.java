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
package com.pt.ptdealerproc.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pt.ptcommon.constant.CommonConstants;
import com.pt.ptcommon.util.IdUtils;
import com.pt.ptdealerproc.dto.MissionDto;
import com.pt.ptdealerproc.dto.ProcessDto;
import com.pt.ptdealerproc.entity.ProcNodeWorker;
import com.pt.ptdealerproc.entity.ProcProcess;
import com.pt.ptdealerproc.mapper.ProcProcessMapper;
import com.pt.ptdealerproc.service.ProcNodeWorkerService;
import com.pt.ptdealerproc.service.ProcProcessService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 加工流程表
 *
 * @author pig code generator
 * @date 2020-04-19 14:58:16
 */
@Service
@AllArgsConstructor
public class ProcProcessServiceImpl extends ServiceImpl<ProcProcessMapper, ProcProcess> implements ProcProcessService {
	private final ProcNodeWorkerService procNodeWorkerService;
	/**
	 * 保存加工流程表
	 * @param processDto
	 * @return
	 */
	@Override
	public Boolean saveProcess(ProcessDto processDto) {
		ProcProcess procProcess = new ProcProcess();
		BeanUtils.copyProperties(processDto,procProcess);
		procProcess.setProcessId(IdUtils.simpleUUID());
		procProcess.setCreateTime(LocalDateTime.now());
		procProcess.setUpdateTime(LocalDateTime.now());
		procProcess.setDelFlag(CommonConstants.STATUS_NORMAL);
		baseMapper.insert(procProcess);
		List<ProcNodeWorker> procNodeWorkerList = processDto.getDynamic().stream().map(node_worker -> {
			ProcNodeWorker procNodeWorker = new ProcNodeWorker();
			procNodeWorker.setProcessId(procProcess.getProcessId());
			procNodeWorker.setNodeId(node_worker.getNodeId());
			procNodeWorker.setWorkerId(node_worker.getWorkerId());
			procNodeWorker.setStartTime(node_worker.getStartTime());
			procNodeWorker.setEndTime(node_worker.getEndTime());
			return procNodeWorker;
		}).collect(Collectors.toList());

		return procNodeWorkerService.saveBatch(procNodeWorkerList);
	}

	/**
	 * 根据id删除加工流程表
	 * @param id
	 * @return
	 */
	@Override
	public Boolean removeProcessById(Integer id) {
		this.removeById(id);
		return Boolean.TRUE;
	}

	/**
	 * 获取加工流程表
	 * @param page
	 * @param processDto
	 * @return
	 */
	@Override
	public IPage getProcessPage(Page page, ProcessDto processDto) {
		return baseMapper.getProcessDtoPage(page,processDto);
	}

	/**
	 * 更新加工流程表
	 * @param processDto
	 * @return
	 */
	@Override
	public Boolean updateProcessById(ProcessDto processDto) {
		ProcProcess procProcess = new ProcProcess();
		BeanUtils.copyProperties(processDto,procProcess);
		procProcess.setUpdateTime(LocalDateTime.now());
		this.updateById(procProcess);
		procNodeWorkerService.remove(Wrappers.<ProcNodeWorker>update().lambda()
			.eq(ProcNodeWorker::getProcessId,procProcess.getProcessId()));
		List<ProcNodeWorker> procNodeWorkerList = processDto.getDynamic().stream().map(node_worker -> {
			ProcNodeWorker procNodeWorker = new ProcNodeWorker();
			procNodeWorker.setProcessId(procProcess.getProcessId());
			procNodeWorker.setNodeId(node_worker.getNodeId());
			procNodeWorker.setWorkerId(node_worker.getWorkerId());
			procNodeWorker.setStartTime(node_worker.getStartTime());
			procNodeWorker.setEndTime(node_worker.getEndTime());
			return procNodeWorker;
		}).collect(Collectors.toList());
		procNodeWorkerService.saveBatch(procNodeWorkerList);
		return Boolean.TRUE;
	}

	@Override
	public IPage getMissionPage(Page page, MissionDto missionDto) {
		return baseMapper.getMissionDtoPage(page,missionDto);
	}
}
