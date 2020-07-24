package com.pt.ptuser.service.serviceImpl;

import com.pt.ptcommoncore.constant.*;
import com.pt.ptuser.entity.SysUserRole;
import com.pt.ptuser.mapper.SysUserRoleMapper;
import com.pt.ptuser.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl implements SysUserRoleService{
    private final SysUserRoleMapper sysUserRoleMapper;
    
    @Override
    public List<SysUserRole> getListByUserId(String userId,String companyId) {
        return sysUserRoleMapper.getUserRoleList(userId,"",companyId);
    }

    @Override
    public Boolean isAdmin(String userId,String companyId) {
        return sysUserRoleMapper.getUserRoleList(userId, CommonConstants.ROLE_ADMIN,companyId).size() != 0;
    }


    @Override
    public Boolean deleteUserRoleByUserId(String userId,String companyId) {
        return sysUserRoleMapper.deleteUserRoleByUserId(userId,companyId);
    }

    @Override
    public Boolean deleteUserRole(String[] ids,String companyId) {
        return sysUserRoleMapper.deleteUserRole(ids,companyId);
    }

    @Override
    public Boolean batchUserRole(List<SysUserRole> userRoleList,String companyId) {
        return sysUserRoleMapper.batchUserRole(userRoleList,companyId);
    }

    @Override
    public Boolean deleteUserRoleInfo(SysUserRole userRole,String companyId) {
        return sysUserRoleMapper.deleteUserRoleInfo(userRole,companyId);
    }

    @Override
    public Boolean deleteUserRoleInfos(String roleId, String[] userIds,String companyId) {
        return sysUserRoleMapper.deleteUserRoleInfos(roleId,userIds,companyId);
    }
}
