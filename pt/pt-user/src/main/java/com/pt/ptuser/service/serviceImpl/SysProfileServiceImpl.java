package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysDept;
import com.pt.ptuser.entity.SysPost;
import com.pt.ptuser.entity.SysRole;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.mapper.SysPostMapper;
import com.pt.ptuser.mapper.SysRoleMapper;
import com.pt.ptuser.mapper.SysUserMapper;
import com.pt.ptuser.service.SysDeptService;
import com.pt.ptuser.service.SysProfileService;
import com.pt.ptuser.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author wl
 */
@Service
@AllArgsConstructor
public class SysProfileServiceImpl implements SysProfileService {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private SysUserMapper sysUserMapper;
    private SysRoleMapper sysRoleMapper;
    private SysPostMapper sysPostMapper;
    private SysUserService sysUserService;
    private SysDeptService sysDeptService;

    @Override
    public Boolean updateUserProfile(SysUser sysUser) {
        if(!sysUserMapper.updateUser(sysUser,SecurityUtils.getCompanyId())){
            throw new CustomException("信息修改错误，请联系管理员!");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updatePwd(String oldPassword, String newPassword) {
        SysUser sysUser = sysUserMapper.getByUserIdAndCompanyId(SecurityUtils.getId(),SecurityUtils.getCompanyId());
        if(!ENCODER.matches(oldPassword,sysUser.getPassword())){
            throw new CustomException("密码修改失败，旧密码输入错误!");
        }
        if(oldPassword.equals(newPassword)){
            throw new CustomException("新密码不能与旧密码相同!");
        }
        if(!sysUserMapper.resetUserPwd(SecurityUtils.getUserName(),ENCODER.encode(newPassword),SecurityUtils.getCompanyId())){
            throw new CustomException("密码修改失败，请联系管理员!");
        }
        return Boolean.TRUE;
    }

    @Override
    public Map<String, String> profile() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        SysUser sysUser = sysUserService.getByUserIdAndCompanyId(SecurityUtils.getId(),SecurityUtils.getCompanyId());
        Map<String, String> result = BeanUtils.describe(sysUser);
        SysDept sysDept = sysDeptService.selectDeptById(sysUser.getDeptId(), SecurityUtils.getCompanyId());
        if(sysDept!=null){
            result.put("deptName",sysDept.getDeptName());
        }
        result.put("roleGroup", selectUserRoleGroup(SecurityUtils.getUserName(), SecurityUtils.getCompanyId()));
        result.put("postGroup", selectUserPostGroup(SecurityUtils.getUserName(), SecurityUtils.getCompanyId()));
        return result;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName,String companyId)
    {
        List<SysRole> list = sysRoleMapper.selectRolesByUserName(userName,companyId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StrUtil.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserPostGroup(String userName,String companyId)
    {
        List<SysPost> list = sysPostMapper.selectPostsByUserName(userName,companyId);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StrUtil.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }
}
