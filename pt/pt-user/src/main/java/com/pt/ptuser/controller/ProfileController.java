package com.pt.ptuser.controller;


import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.mapper.SysUserMapper;
import com.pt.ptuser.service.SysDeptService;
import com.pt.ptuser.service.SysProfileService;
import com.pt.ptuser.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

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
        return R.ok(sysProfileService.profile());
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
        boolean save = sysUserMapper.updateUser(byUserId,SecurityUtils.getCompanyId());
        return R.ok();
    }



}
