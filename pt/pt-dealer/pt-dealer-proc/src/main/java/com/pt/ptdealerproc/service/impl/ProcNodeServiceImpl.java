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


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptdealerproc.entity.ProcNode;
import com.pt.ptdealerproc.mapper.ProcNodeMapper;
import com.pt.ptdealerproc.service.ProcNodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加工节点表
 *
 * @author pig code generator
 * @date 2020-04-19 10:46:44
 */
@Service
@AllArgsConstructor
public class ProcNodeServiceImpl  implements ProcNodeService {

	private final ProcNodeMapper procNodeMapper;
	@Override
	public IPage getProcNodePage(Page page, ProcNode procNode,String companyId) {
		return procNodeMapper.getProcNodePage(page,procNode,companyId);
	}

	/**
	 * 查询节点信息集合
	 *
	 * @param procNode 节点信息
	 * @return 节点信息集合
	 */
	@Override
	public List<ProcNode> selectNodeList(ProcNode procNode,String companyId)
	{
		return procNodeMapper.selectNodeList(procNode,companyId);
	}



	/**
	 * 查询所有节点
	 *
	 * @return 节点列表
	 */
	@Override
	public List<ProcNode> selectNodeAll(String companyId)
	{
		return procNodeMapper.selectNodeAll(companyId);
	}

	/**
	 * 通过节点ID查询节点信息
	 *
	 * @param nodeId 节点ID
	 * @return 角色对象信息
	 */
	@Override
	public ProcNode selectNodeById(String nodeId,String companyId)
	{
		return procNodeMapper.selectNodeById(nodeId,companyId);
	}
	

	/**
	 * 校验节点名称是否唯一
	 *
	 * @param node 节点信息
	 * @return 结果
	 */
	@Override
	public Boolean checkNodeNameUnique(ProcNode node,String companyId)
	{
		if(StrUtil.isEmpty(node.getNodeId())){
			return Boolean.TRUE;
		}
		ProcNode procNode = procNodeMapper.checkNodeNameUnique(node.getNodeName(),companyId);
		if (procNode != null && !procNode.getNodeId().equals(node.getNodeId()))
		{
			throw new CustomException("修改节点'" + procNode.getNodeName() + "'失败，节点名称已存在");
		}
		return Boolean.TRUE;

	}

	/**
	 * 校验节点编码是否唯一
	 *
	 * @param node 节点信息
	 * @return 结果
	 */
	@Override
	public Boolean checkNodeCodeUnique(ProcNode node,String companyId)
	{
		if(StrUtil.isEmpty(node.getNodeId())){
			return Boolean.TRUE;
		}
		ProcNode procNode = procNodeMapper.checkNodeCodeUnique(node.getNodeCode(),companyId);
		if (procNode != null && !procNode.getNodeId().equals(node.getNodeId()))
		{
			throw new CustomException("修改节点'" + procNode.getNodeName() + "'失败，节点编码已存在");
		}
		return Boolean.TRUE;
	}

	/**
	 * 通过节点ID查询节点使用数量
	 *
	 * @param nodeId 节点ID
	 * @return 结果
	 */
	@Override
	public int countProcNodeById(String nodeId,String companyId)
	{
//		return sysUserNodeService.countProcNodeById(nodeId);
		return 0;
	}

	/**
	 * 删除节点信息
	 *
	 * @param nodeId 节点ID
	 * @return 结果
	 */
	@Override
	public Boolean deleteNodeById(String nodeId,String companyId)
	{
		return procNodeMapper.deleteNodeById(nodeId,companyId);
	}

	/**
	 * 批量删除节点信息
	 *
	 * @param nodeIds 需要删除的节点ID
	 * @return 结果
	 * @throws Exception 异常
	 */
	@Override
	public Boolean deleteNodeByIds(String[] nodeIds,String companyId)
	{
		for (String nodeId : nodeIds)
		{
			if (countProcNodeById(nodeId,companyId) > 0)
			{
				return  Boolean.FALSE;
			}
		}
		return procNodeMapper.deleteNodeByIds(nodeIds,companyId);
	}

	/**
	 * 新增保存节点信息
	 *
	 * @param node 节点信息
	 * @return 结果
	 */
	@Override
	public Boolean insertNode(ProcNode node,String companyId)
	{
		node.setNodeId(IdUtils.simpleUUID());
		return procNodeMapper.insertNode(node,companyId);
	}

	/**
	 * 修改保存节点信息
	 *
	 * @param node 节点信息
	 * @return 结果
	 */
	@Override
	public Boolean updateNode(ProcNode node,String companyId)
	{
		return procNodeMapper.updateNode(node,companyId);
	}

	@Override
	public List<ProcNode> getProcNodeList(String companyId) {
		return procNodeMapper.getProcNodeList(companyId);
	}
}
