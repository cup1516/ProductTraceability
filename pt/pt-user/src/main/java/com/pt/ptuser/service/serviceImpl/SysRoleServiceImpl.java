package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.constant.*;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysRole;
import com.pt.ptuser.entity.SysRoleMenu;
import com.pt.ptuser.mapper.SysRoleMapper;
import com.pt.ptuser.mapper.SysUserRoleMapper;
import com.pt.ptuser.service.SysRoleMenuService;
import com.pt.ptuser.service.SysRoleService;
import com.pt.ptuser.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysRoleServiceImpl  implements SysRoleService {
    private SysRoleMenuService roleMenuService;
    private SysUserRoleService sysUserRoleService;
    private SysUserRoleMapper sysUserRoleMapper;
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> findRolesByUserId(String UserId, String companyId) {
        return sysRoleMapper.listRolesByUserIdAndCompanyId(UserId,companyId);
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<String> selectRoleListByUserId(String userId,String companyId)
    {
        return sysRoleMapper.selectRoleListByUserId(userId,companyId);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll(String companyId)
    {
        return selectRoleList(new SysRole(),companyId);
    }

    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role,String companyId)
    {
        return sysRoleMapper.selectRoleList(role,companyId);
    }

    /**
     * 根据角色名获取角色
     * @param roleCode
     * @return
     */
    @Override
    public SysRole getByRoleCode(String roleCode,String companyId) {
        return sysRoleMapper.getByRoleCode(roleCode,companyId);
    }

    @Override
    public IPage<List<SysRole>> getRolePage(Page page, SysRole sysRole,String companyId) {
        return sysRoleMapper.getRolePage(page,sysRole,companyId);
    }

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    @Override
    public SysRole selectRoleById(String roleId,String companyId) {
        return sysRoleMapper.selectRoleById(roleId,companyId);
    }

    /**
     * 检验是否为管理员
     * @param role 角色信息
     * @return
     */
    @Override
    public Boolean checkRoleAllowed(SysRole role,String companyId) {
        if (StrUtil.isNotEmpty(role.getRoleId()) &&this.selectRoleById(role.getRoleId(),companyId)
                .getRoleCode().equals(CommonConstants.ROLE_ADMIN)) {
            throw new CustomException("修改用户'" + role.getRoleName() + "'失败，无修改权限");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateRoleStatus(SysRole role,String companyId) {
        return sysRoleMapper.updateRole(role,companyId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean checkRoleNameUnique(SysRole role,String companyId)
    {

        SysRole sysRole = sysRoleMapper.checkRoleNameUnique(role.getRoleName(),companyId);
        if(sysRole != null && !sysRole.getRoleId().equals(role.getRoleId())){
            throw new CustomException("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean checkRoleCodeUnique(SysRole role,String companyId)
    {

        SysRole sysRole = sysRoleMapper.checkRoleCodeUnique(role.getRoleCode(),companyId);
        if(sysRole != null && !sysRole.getRoleId().equals(role.getRoleId())){
            throw new CustomException("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return Boolean.TRUE;
    }
    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean updateRole(SysRole role,String companyId) {
        // 修改角色信息
        sysRoleMapper.updateRole(role,companyId);
        // 删除角色与菜单关联
        roleMenuService.deleteRoleMenuByRoleId(role.getRoleId(),SecurityUtils.getCompanyId());
        return insertRoleMenu(role);
    }
    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean insertRole(SysRole role,String companyId) {
        role.setRoleId(IdUtils.simpleUUID());
        // 新增角色信息
        sysRoleMapper.insertRole(role,companyId);
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public Boolean insertRoleMenu(SysRole role)
    {

        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (String menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            return roleMenuService.batchRoleMenu(list, SecurityUtils.getCompanyId());
        }
        return Boolean.FALSE;
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    public Boolean deleteRoleByIds(String[] roleIds,String companyId)
    {
        SysRole sysRole = new SysRole();
        for (String roleId : roleIds)
        {
            checkRoleAllowed(sysRole,companyId);
            SysRole role = selectRoleById(roleId,companyId);
            if (countUserRoleByRoleId(roleId,companyId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return sysRoleMapper.deleteRoleByIds(roleIds,companyId);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public Integer countUserRoleByRoleId(String roleId,String companyId)
    {
        return sysUserRoleMapper.countUserRoleByRoleId(roleId,companyId);
    }

}
