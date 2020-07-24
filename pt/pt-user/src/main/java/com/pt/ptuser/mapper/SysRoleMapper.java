package com.pt.ptuser.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptuser.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper  {
    List<SysRole> listRolesByUserIdAndCompanyId(@Param("userId") String userId, @Param("companyId") String companyId);
    List<SysRole> listRolesByUserId(@Param("userId") String userId);
    SysRole getByRoleCode(@Param("roleCode") String roleCode, @Param("companyId") String companyId);
    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    List<String> selectRoleListByUserId(@Param("userId") String userId,@Param("companyId") String companyId);
    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(@Param("role")SysRole role,@Param("companyId") String companyId);

    /**
     * 分页获取角色
     * @param page 分页参数
     * @param sysRole 角色实体
     * @return
     */
    IPage<List<SysRole>> getRolePage(Page page, @Param("role") SysRole sysRole,@Param("companyId") String companyId);

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    SysRole selectRoleById(@Param("roleId") String roleId,@Param("companyId") String companyId);
    /**
     * 判断是否为指定角色
     * @param roleId 角色Id
     * @param roleCode  角色编码
     * @param companyId 公司Id
     * @return
     */
    SysRole checkRole(@Param("roleId") String roleId, @Param("roleCode") String roleCode, @Param("companyId") String companyId);
    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    Boolean updateRole(@Param("role") SysRole role,@Param("companyId") String companyId);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    SysRole checkRoleNameUnique(@Param("roleName") String roleName,@Param("companyId") String companyId);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleCode 角色权限
     * @return 角色信息
     */
    SysRole checkRoleCodeUnique(@Param("roleCode") String roleCode,@Param("companyId") String companyId);



    /**
     * 新增角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    Boolean insertRole(@Param("role") SysRole role,@Param("companyId") String companyId);
    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    Boolean deleteRoleByIds(@Param("roles") String[] roleIds,@Param("companyId") String companyId);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    Boolean deleteRoleById(@Param("roleId") String roleId,@Param("companyId") String companyId);

    /**
     * 根据用户ID查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserName(@Param("userName") String userName, @Param("companyId") String companyId);
}