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

package com.pt.ptdealerproc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerproc.entity.ProcNode;

import java.util.List;

/**
 * 加工节点表
 *
 * @author pig code generator
 * @date 2020-04-19 10:46:44
 */
public interface ProcNodeService  {


	IPage getProcNodePage(Page page, ProcNode procNode,String companyId);

	/**
	 * 查询节点信息集合
	 *
	 * @param procNode 节点信息
	 * @return 节点列表
	 */
	List<ProcNode> selectNodeList(ProcNode procNode,String companyId);

	/**
	 * 查询所有节点
	 *
	 * @return 节点列表
	 */
	List<ProcNode> selectNodeAll(String companyId);

	/**
	 * 通过节点ID查询节点信息
	 *
	 * @param nodeId 节点ID
	 * @return 角色对象信息
	 */
	ProcNode selectNodeById(String nodeId,String companyId);
	
	/**
	 * 校验节点名称
	 *
	 * @param procNode 节点信息
	 * @return 结果
	 */
	Boolean checkNodeNameUnique(ProcNode procNode,String companyId);

	/**
	 * 校验节点编码
	 *
	 * @param procNode 节点信息
	 * @return 结果
	 */
	Boolean checkNodeCodeUnique(ProcNode procNode,String companyId);

	/**
	 * 通过节点ID查询节点使用数量
	 *
	 * @param nodeId 节点ID
	 * @return 结果
	 */
	int countProcNodeById(String nodeId,String companyId);

	/**
	 * 删除节点信息
	 *
	 * @param nodeId 节点ID
	 * @return 结果
	 */
	Boolean deleteNodeById(String nodeId,String companyId);

	/**
	 * 批量删除节点信息
	 *
	 * @param nodeIds 需要删除的节点ID
	 * @return 结果
	 * @throws Exception 异常
	 */
	Boolean deleteNodeByIds(String[] nodeIds,String companyId);

	/**
	 * 新增保存节点信息
	 *
	 * @param procNode 节点信息
	 * @return 结果
	 */
	Boolean insertNode(ProcNode procNode,String companyId);

	/**
	 * 修改保存节点信息
	 *
	 * @param procNode 节点信息
	 * @return 结果
	 */
	Boolean updateNode(ProcNode procNode,String companyId);

	/**
	 * 获取节点列表
	 * @return
	 */
	List<ProcNode> getProcNodeList(String companyId);
}
