package com.pt.ptuser.service;

import com.pt.ptuser.dto.TreeSelect;
import com.pt.ptuser.entity.SysDept;

import java.util.List;

public interface SysDeptService  {

    /**
     * 获取下级部门列表
     * @param sysDept 部门
     * @return
     */
    List<SysDept> selectDeptList( SysDept sysDept,String companyId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
     Boolean checkDeptExistUser(String deptId,String companyId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
     Boolean checkDeptNameUnique(SysDept dept,String companyId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
     Boolean insertDept(SysDept dept,String companyId);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(String deptId,String companyId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    Boolean deleteDeptById(String deptId,String companyId);
    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    Boolean hasChildByDeptId(String deptId,String companyId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(String deptId,String companyId);
    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept,String companyId);
}
