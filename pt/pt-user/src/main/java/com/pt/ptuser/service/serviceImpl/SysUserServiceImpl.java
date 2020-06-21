package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pt.ptcommon.constant.CommonConstants;
import com.pt.ptcommon.exception.CustomException;
import com.pt.ptcommon.util.IdUtils;
import com.pt.ptcommon.util.R;
import com.pt.ptuser.dto.UserInfo;
import com.pt.ptuser.entity.*;
import com.pt.ptuser.mapper.SysUserMapper;
import com.pt.ptuser.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wl
 * @date 2020/5/18
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private SysRoleService sysRoleService;
    private SysMenuService sysMenuService;
    private SysUserRoleService sysUserRoleService;
    private SysUserPostService sysUserPostService;
    private final SysUserMapper sysUserMapper;
    private SysRoleMenuService sysRoleMenuService;

    /**
     * auth通过账号与客户端获取用户信息
     * @param username
     * @param clientId
     * @return
     */
    @Override
    public UserInfo findUserByUsername(String username, String clientId) {
        SysUser sysUser = sysUserMapper.findUserByUsername(username, clientId);
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表  （ID）
        List<SysRole> dealerSysRoles = sysRoleService.findRolesByUserId(sysUser.getUserId(),clientId);
        List<String> roles = dealerSysRoles.stream()
                .map(SysRole::getRoleCode)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roles, String.class));
        //设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        dealerSysRoles.forEach(dealerRole -> {
            List<String> permissionsByRoleId = sysMenuService.findPermissionsByRoleId(dealerRole.getRoleId(),clientId);
            permissions.addAll(permissionsByRoleId);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public IPage getDeptUserWithRolePage(Page page, String clientId,String deptId) {
        return sysUserMapper.getDeptUserPage(page,clientId,deptId);
    }

    @Override
    public IPage getAllUserWithRolePage(Page page, String clientId) {
        return sysUserMapper.getAllUserPage(page,clientId);
    }

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    @Override
    public SysUser getByUserId(String userId) {
        return sysUserMapper.getByUserId(userId);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public Boolean checkUserAllowed(SysUser user)
    {
        if (StrUtil.isNotEmpty(user.getUserId()) && sysUserRoleService.isAdmin(user.getUserId())) {
            throw new CustomException("修改用户'" + user.getUserName() + "'失败，无修改权限");
        }
        return Boolean.TRUE;
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean updateUserStatus(SysUser user)
    {
        return sysUserMapper.updateUser(user);
    }

    @Override
    public Boolean insertUser(SysUser user) {
        user.setUserId(IdUtils.simpleUUID());
        user.setPassword(ENCODER.encode(user.getPassword()));
        insertUserPost(user);
        insertUserRole(user);
        return sysUserMapper.insertUser(user);
    }
    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        String[] roles = user.getRoleIds();
        if (roles.length>0)
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                sysUserRoleService.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        String[] posts = user.getPostIds();
        if (posts.length>0)
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (String postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                sysUserPostService.batchUserPost(list);
            }
        }
    }
    @Override
    public Boolean checkUserNameUnique(SysUser user) {
        SysUser sysUser = sysUserMapper.checkUserNameUnique(user.getUserName());
        if(sysUser != null){
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkPhoneUnique(SysUser user) {
        if(StrUtil.isEmpty(user.getUserId())){
            return Boolean.TRUE;
        }
        SysUser sysUser = sysUserMapper.checkPhoneUnique(user.getPhone());

        if (sysUser != null && !sysUser.getUserId().equals(sysUser.getUserId()))
        {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkEmailUnique(SysUser user) {
        if(StrUtil.isEmpty(user.getUserId())){
            return Boolean.TRUE;
        }
        SysUser sysUser = sysUserMapper.checkEmailUnique(user.getEmail());

        if (sysUser != null && !sysUser.getUserId().equals(sysUser.getUserId()))
        {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return Boolean.TRUE;
    }
    /**
     * 重置用户密码
     *
     * @param sysUser
     * @return 结果
     */
    @Override
    public Boolean resetUserPwd(SysUser sysUser)
    {
        return sysUserMapper.resetUserPwd(sysUser);
    }
    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public Boolean deleteUserByIds(String[] userIds)
    {
        for (String userId : userIds)
        {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            if(checkUserAllowed(sysUser)){
                return Boolean.FALSE;
            }
        }
        return sysUserMapper.deleteUserByIds(userIds);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public Boolean deleteUserById(String userId)
    {
        // 删除用户与角色关联
        sysUserRoleService.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        sysUserPostService.deleteUserPostByUserId(userId);
        return sysUserMapper.deleteUserById(userId);
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean updateUser(SysUser user)
    {
        String userId = user.getUserId();
        // 删除用户与角色关联
        sysUserRoleService.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        sysUserPostService.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return sysUserMapper.updateUser(user);
    }
    /**
     * 获取用户列表
     * @return
     */
    @Override
    public List<SysUser> listUser() {
        return sysUserMapper.listUser();
    }
    /**
     * 根据权限获取用户列表
     * @param deptId
     * @return
     */
    @Override
    public List<SysUser> listUserByDept(String deptId) {
        return sysUserMapper.listUserByDept(deptId);
    }
    /**
     * 根据权限获取用户列表
     * @param perms
     * @return
     */
    @Override
    public List<SysUser> listUserByPerms(String[] perms) {
        return sysUserMapper.listUserByPerms(perms);
    }
}
