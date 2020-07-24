package com.pt.ptuser.controller;


import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.mapper.SysUserMapper;
import com.pt.ptuser.service.SysDeptService;
import com.pt.ptuser.service.SysProfileService;
import com.pt.ptuser.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 个人信息 业务处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/user/profile")
@AllArgsConstructor
public class ProfileController
{

    private SysUserService sysUserService;
    private SysDeptService sysDeptService;
    private SysProfileService sysProfileService;
    private SysUserMapper sysUserMapper;



    /**
     * 个人信息
     */
    @GetMapping
    public R profile() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        SysUser sysUser = sysUserService.getByUserIdAndCompanyId(SecurityUtils.getId(),SecurityUtils.getCompanyId());
        Map<String, String> result = BeanUtils.describe(sysUser);
        result.put("deptName",sysDeptService.selectDeptById(sysUser.getDeptId(), SecurityUtils.getCompanyId()).getDeptName());
        result.put("roleGroup", sysUserService.selectUserRoleGroup(SecurityUtils.getUserName(), SecurityUtils.getCompanyId()));
        result.put("postGroup", sysUserService.selectUserPostGroup(SecurityUtils.getUserName(), SecurityUtils.getCompanyId()));
        return R.ok(result);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public R updateProfile(@RequestBody SysUser user)
    {
        sysProfileService.updateUserProfile(user);
        return R.ok(sysUserService.getByUserIdAndCompanyId(SecurityUtils.getId(),SecurityUtils.getCompanyId()));
    }

    /**
     * 重置密码
     */
    @PutMapping("/updatePwd")
    public R updatePwd(String oldPassword, String newPassword)
    {
        sysProfileService.updatePwd(oldPassword,newPassword);
        return R.ok();
    }

    @PostMapping("/updateAvatar")
    public  R updateAvatar(@RequestBody  String avatar){

        SysUser byUserId = sysUserMapper.getByUserIdAndCompanyId(SecurityUtils.getId(),SecurityUtils.getCompanyId());
        byUserId.setAvatar(avatar);
        boolean save = sysUserMapper.updateUser(byUserId);
        return R.ok();
    }



}
