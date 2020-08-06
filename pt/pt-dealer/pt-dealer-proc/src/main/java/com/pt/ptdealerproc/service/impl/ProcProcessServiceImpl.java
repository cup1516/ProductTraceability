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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerproc.dto.ProcessDto;
import com.pt.ptdealerproc.entity.ProcProcess;
import com.pt.ptdealerproc.entity.ProcProcessNode;
import com.pt.ptdealerproc.mapper.ProcProcessMapper;
import com.pt.ptdealerproc.mapper.ProcProcessNodeMapper;
import com.pt.ptdealerproc.service.ProcProcessService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加工流程表
 *
 * @author pig code generator
 * @date 2020-04-19 14:58:16
 */
@Service
@AllArgsConstructor
public class ProcProcessServiceImpl  implements ProcProcessService {

	private final ProcProcessNodeMapper procProcessNodeMapper;
	private final ProcProcessMapper procProcessMapper;
	/**
	 * 获取加工流程表
	 * @param page
	 * @param processDto
	 * @return
	 */
	@Override
	public IPage getProcessPage(Page page, ProcessDto processDto,String companyId) {
		processDto.setCreateBy(SecurityUtils.getUserName());
		return procProcessMapper.getProcessDtoPage(page,processDto,companyId);
	}
	/**
	 * 获取加工流程表
	 * @param page
	 * @param processDto
	 * @return
	 */
	@Override
	public IPage getProcessCheckPage(Page page, ProcessDto processDto,String companyId) {
		return procProcessMapper.getProcessDtoCheckPage(page,processDto,companyId);
	}
	/**
	 * 查询流程信息集合
	 *
	 * @param procProcess 流程信息
	 * @return 流程信息集合
	 */
	@Override
	public List<ProcProcess> selectProcessList(ProcProcess procProcess,String companyId)
	{
		return procProcessMapper.selectProcessList(procProcess,companyId);
	}



	/**
	 * 查询所有流程
	 *
	 * @return 流程列表
	 */
	@Override
	public List<ProcProcess> selectProcessAll(String companyId)
	{
		return procProcessMapper.selectProcessAll(companyId);
	}

	/**
	 * 通过流程ID查询流程信息
	 *
	 * @param processId 流程ID
	 * @return 角色对象信息
	 */
	@Override
	public ProcessDto selectProcessById(String processId,String companyId)
	{
		ProcessDto processDto = procProcessMapper.selectProcessById(processId, companyId);
		return procProcessMapper.selectProcessById(processId,companyId);
	}


	/**
	 * 校验流程名称是否唯一
	 *
	 * @param process 流程信息
	 * @return 结果
	 */
	@Override
	public Boolean checkProcessNameUnique(ProcProcess process,String companyId)
	{
//		if(StrUtil.isEmpty(process.getProcessId())){
//			return Boolean.TRUE;
//		}
		ProcProcess procProcess = procProcessMapper.checkProcessNameUnique(process.getProcessName(),companyId);

		if (procProcess != null && !procProcess.getProcessId().equals(process.getProcessId()))
		{
			throw new CustomException("修改流程'" + procProcess.getProcessName() + "'失败，流程名称已存在");
		}
		return Boolean.TRUE;

	}

	/**
	 * 校验流程编码是否唯一
	 *
	 * @param process 流程信息
	 * @return 结果
	 */
	@Override
	public Boolean checkProcessCodeUnique(ProcProcess process,String companyId)
	{
//		if(StrUtil.isEmpty(process.getProcessId())){
//			return Boolean.TRUE;
//		}
		ProcProcess procProcess = procProcessMapper.checkProcessCodeUnique(process.getProcessCode(),companyId);

		if (procProcess != null && !procProcess.getProcessId().equals(process.getProcessId()))
		{
			throw new CustomException("修改流程'" + procProcess.getProcessCode() + "'失败，流程编码已存在");
		}
		return Boolean.TRUE;
	}

	/**
	 * 通过流程ID查询流程使用数量
	 *
	 * @param processId 流程ID
	 * @return 结果
	 */
	@Override
	public int countProcProcessById(String processId,String companyId)
	{
//		return sysUserProcessService.countProcProcessById(processId);
		return 0;
	}

	/**
	 * 删除流程信息
	 *
	 * @param processId 流程ID
	 * @return 结果
	 */
	@Override
	public Boolean deleteProcessById(String processId,String companyId)
	{
		return procProcessMapper.deleteProcessById(processId,companyId);
	}

	/**
	 * 批量删除流程信息
	 *
	 * @param processIds 需要删除的流程ID
	 * @return 结果
	 * @throws Exception 异常
	 */
	@Override
	public Boolean deleteProcessByIds(String[] processIds,String companyId)
	{
		for (String processId : processIds)
		{
			procProcessNodeMapper.deleteProcessNode(processId,companyId);
//			if (countProcProcessById(processId) > 0)
//			{
//				return  Boolean.FALSE;
//			}
		}

		procProcessMapper.deleteProcessByIds(processIds,companyId);
		return Boolean.TRUE;
	}

	/**
	 * 新增保存流程信息
	 *
	 * @param processDto 流程信息
	 * @return 结果
	 */
	@Override
	public Boolean insertProcess(ProcessDto processDto,String companyId)
	{
		processDto.setProcessId(IdUtils.simpleUUID());
		procProcessMapper.insertProcess(processDto,companyId);
		List<ProcProcessNode> processNodes = processDto.getProcessNodes();
		processNodes.stream().forEach(procProcessNode -> procProcessNode.setProcessId(processDto.getProcessId()));
		procProcessNodeMapper.batchProcessNode(processNodes,companyId);
		return Boolean.TRUE;
	}

	/**
	 * 修改保存流程信息
	 *
	 * @param processDto 流程信息
	 * @return 结果
	 */
	@Override
	public Boolean updateProcess(ProcessDto processDto,String companyId)
	{
		List<ProcProcessNode> processNodes = processDto.getProcessNodes();
		processNodes.stream().forEach(procProcessNode -> procProcessNode.setProcessId(processDto.getProcessId()));
		procProcessNodeMapper.deleteProcessNode(processDto.getProcessId(),companyId);
		if(processDto.getProcessNodes().size()>0){
			procProcessNodeMapper.batchProcessNode(processNodes,companyId);
		}
		return procProcessMapper.updateProcess(processDto,companyId);
	}

	@Override
	public List<ProcProcess> getProcProcessList(String companyId) {
		return procProcessMapper.getProcProcessList(companyId);
	}

	@Override
	public Boolean changeCheckStatus(ProcessDto processDto,String companyId) {
		return procProcessMapper.changeCheckStatus(processDto,companyId);
	}
}
