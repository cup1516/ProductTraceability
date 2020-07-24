package com.pt.ptuser.service;

import com.pt.ptuser.entity.SysUserRole;

import java.util.List;

/**
 * @author wl
 */
public interface SysUserRoleService  {

    /**
     * 获取用户对应角色列表
     * @param userId
     * @return
     */
    List<SysUserRole> getListByUserId(String userId,String companyId);

    /**
     * 是否为管理员
     * @param userId
     * @return
     */
    Boolean isAdmin(String userId,String companyId);
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    Boolean deleteUserRoleByUserId(String userId,String companyId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    Boolean deleteUserRole(String[] ids,String companyId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    Boolean batchUserRole(List<SysUserRole> userRoleList,String companyId);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    Boolean deleteUserRoleInfo(SysUserRole userRole,String companyId);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    Boolean deleteUserRoleInfos(String roleId, String[] userIds,String companyId);


}
