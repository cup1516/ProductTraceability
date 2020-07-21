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
    public List<SysUserRole> getListByUserId(String userId) {
        return sysUserRoleMapper.getUserRoleList(userId,"");
    }

    @Override
    public Boolean isAdmin(String userId) {
        return sysUserRoleMapper.getUserRoleList(userId, CommonConstants.ROLE_ADMIN).size() != 0;
    }


    @Override
    public Boolean deleteUserRoleByUserId(String userId) {
        return sysUserRoleMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public Boolean deleteUserRole(String[] ids) {
        return sysUserRoleMapper.deleteUserRole(ids);
    }

    @Override
    public Boolean batchUserRole(List<SysUserRole> userRoleList) {
        return sysUserRoleMapper.batchUserRole(userRoleList);
    }

    @Override
    public Boolean deleteUserRoleInfo(SysUserRole userRole) {
        return sysUserRoleMapper.deleteUserRoleInfo(userRole);
    }

    @Override
    public Boolean deleteUserRoleInfos(String roleId, String[] userIds) {
        return sysUserRoleMapper.deleteUserRoleInfos(roleId,userIds);
    }
}
