package com.pt.ptuser.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.constant.*;
import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.dto.UserInfo;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.service.SysPostService;
import com.pt.ptuser.service.SysRoleService;
import com.pt.ptuser.service.SysUserService;
import com.pt.ptuser.vo.UserVo;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private SysUserService sysUserService;
    private SysRoleService sysRoleService;
    private SysPostService sysPostService;
    /**
     * auth获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/info/{username}")
    private UserInfo findUserByUsername(@PathVariable String username,@Param("url") String url ){
        return  sysUserService.findUserByUsernameAndUrl(username,url);
    }

    /**
     * 根据token获取当前用户信息
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    public R info() {
        return R.ok(sysUserService.findUserByUsernameAndCompanyId(SecurityUtils.getUserName(),SecurityUtils.getCompanyId()));
    }
    /**
     * 分页查询全部用户
     *
     * @param page    参数集
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getAllUserPage(Page page, UserVo userVo) {
        return R.ok(sysUserService.getAllUserWithRolePage(page,userVo,SecurityUtils.getCompanyId()));
    }

    /**
     * 分页查询本部门用户
     *
     * @param page    参数集
     * @return 用户集合
     */
    @GetMapping("/dept")
    public R getDeptUserPage(Page page) {
        return R.ok(sysUserService.getDeptUserWithRolePage(page,SecurityUtils.getCompanyId(),SecurityUtils.getDeptId()));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = { "/", "/{userId}" })
    public R getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        Map result = new HashMap<String, List<String>>();
        result.put("roles", sysRoleService.selectRoleAll(SecurityUtils.getCompanyId()));
        result.put("posts", sysPostService.selectPostAll(SecurityUtils.getCompanyId()));
        if (StrUtil.isNotEmpty(userId))
        {
            result.put("data", sysUserService.getByUserIdAndCompanyId(userId,SecurityUtils.getCompanyId()));
            result.put("postIds", sysPostService.selectPostListByUserId(userId, SecurityUtils.getCompanyId()));
            result.put("roleIds", sysRoleService.selectRoleListByUserId(userId, SecurityUtils.getCompanyId()));
        }
        return R.ok(result);
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody SysUser user)
    {
        sysUserService.checkUserAllowed(sysUserService.getByUserIdAndCompanyId(user.getUserId(),SecurityUtils.getCompanyId()), SecurityUtils.getCompanyId());
        return R.ok(sysUserService.updateUserStatus(user));
    }

    /**
     * 获取初始密码
     * @return
     */
    @GetMapping("/initPwd")
    public R getInitPassword(){
        return R.ok(CommonConstants.INIT_PASSWORD);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public R add(@Validated @RequestBody SysUser user)
    {
        sysUserService.checkUserNameUnique(user);
        sysUserService.checkPhoneUnique(user);
        sysUserService.checkEmailUnique(user);
//        user.setCreateBy(SecurityUtils.getUsername());
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return R.ok(sysUserService.insertUser(user, SecurityUtils.getCompanyId()));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public R remove(@PathVariable String[] userIds)
    {
        return R.ok(sysUserService.deleteUserByIdsAndCompanyId(userIds,SecurityUtils.getCompanyId()));
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody SysUser user)
    {
        if(sysUserService.checkUserAllowed(user, SecurityUtils.getCompanyId())){
            return R.ok(sysUserService.resetUserPwd(user.getUserName(),CommonConstants.INIT_PASSWORD,SecurityUtils.getCompanyId()));
        }else {
            return R.failed("重置用户'" + user.getUserName() + "'密码失败，无操作权限");
        }
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        user.setUpdateBy(SecurityUtils.getUsername());

    }

    /**
     * 修改用户
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysUser user)
    {
        sysUserService.checkUserAllowed(user, SecurityUtils.getCompanyId());
        sysUserService.checkPhoneUnique(user);
        sysUserService.checkEmailUnique(user);
//        user.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysUserService.updateUser(user, SecurityUtils.getCompanyId()));
    }
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/list")
    public R listUser(){
        return R.ok(sysUserService.listUser(SecurityUtils.getCompanyId()));
    }

    /**
     * 获取部门用户列表
     * @return
     */
    @GetMapping("/list/dept")
    public R listUserByDept(){
        return R.ok(sysUserService.listUserByDept(SecurityUtils.getDeptId(),SecurityUtils.getCompanyId()));
    }
    /**
     * 根据职位获取用户列表
     * @return
     */
    @GetMapping("/list/post/{post}")
    public R listUserByPost(@PathVariable String[] post){
        return R.ok(sysUserService.listUserByPost(post,SecurityUtils.getCompanyId()));
    }

    /**
     * 根据权限获取用户列表
     * @param perms 权限
     * @return
     */
    @GetMapping("/list/perms/{perms}")
    public R listUserByPerms(@PathVariable String[] perms){
        return R.ok(sysUserService.listUserByPerms(perms,SecurityUtils.getCompanyId()));
    }
}
