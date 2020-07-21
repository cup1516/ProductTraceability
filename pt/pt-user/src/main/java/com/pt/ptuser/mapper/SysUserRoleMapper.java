package com.pt.ptuser.mapper;

import com.pt.ptuser.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wl
 */
@Mapper
public interface SysUserRoleMapper  {
    /**
     * 根据
     * @param userId
     * @param roleCode
     * @return
     */
    List<SysUserRole> getUserRoleList(@Param("userId") String userId,@Param("roleCode") String roleCode);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
     Integer countUserRoleByRoleId(@Param("roleId") String roleId,@Param("companyId") String companyId);

    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    Boolean deleteUserRoleByUserId(String userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    Boolean deleteUserRole(String[] ids);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    Boolean batchUserRole(List<SysUserRole> userRoleList);
    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    Boolean batchUserRoleWithClientId(@Param("userRoleList") List<SysUserRole> userRoleList,@Param("clientId") String clientId);
    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    Boolean deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    Boolean deleteUserRoleInfos(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
}