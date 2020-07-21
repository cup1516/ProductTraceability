package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptuser.dto.UserInfo;
import com.pt.ptuser.entity.*;
import com.pt.ptuser.mapper.SysMenuMapper;
import com.pt.ptuser.mapper.SysPostMapper;
import com.pt.ptuser.mapper.SysRoleMapper;
import com.pt.ptuser.mapper.SysUserMapper;
import com.pt.ptuser.service.*;
import com.pt.ptuser.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class SysUserServiceImpl implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private SysRoleService sysRoleService;
    private SysMenuService sysMenuService;
    private SysUserRoleService sysUserRoleService;
    private SysUserPostService sysUserPostService;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPostMapper sysPostMapper;
    private final SysMenuMapper sysMenuMapper;
    /**
     * auth通过账号与客户端获取用户信息
     * @param username
     * @param url
     * @return
     */
    @Override
    public UserInfo findUserByUsernameAndUrl(String username, String url) {
        SysUser sysUser;
        if(url == ""){
            sysUser = sysUserMapper.findUserByUsername(username);
        }else{
            sysUser = sysUserMapper.findUserByUsernameAndUrl(username,url);
        }
        //重新拼接用户名
        sysUser.setUserName(sysUser.getUserName()+'_'+url);
        UserInfo userInfo = new UserInfo();
        if(sysUser!=null){
            userInfo.setSysUser(sysUser);
            //设置角色列表  （ID）
            List<SysRole> dealerSysRoles = sysRoleMapper.listRolesByUserIdAndCompanyId(sysUser.getUserId(),sysUser.getCompanyId());
            List<String> roles = dealerSysRoles.stream()
                    .map(SysRole::getRoleCode)
                    .collect(Collectors.toList());
            userInfo.setRoles(ArrayUtil.toArray(roles, String.class));
            //设置权限列表（menu.permission）
            Set<String> permissions = new HashSet<>();
            dealerSysRoles.forEach(dealerRole -> {
                List<String> permissionsByRoleId = sysMenuService.findPermissionsByRoleIdAndCompanyId(dealerRole.getRoleId(),sysUser.getCompanyId());
                permissions.addAll(permissionsByRoleId);
            });
            userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        }
        return userInfo;
    }
    /**
     * 通过账号与公司ID获取用户信息
     * @param username
     * @param companyId
     * @return
     */
    @Override
    public UserInfo findUserByUsernameAndCompanyId(String username, String companyId) {
        SysUser sysUser;
        if(companyId == ""){
            sysUser = sysUserMapper.findUserByUsername(username);
        }else{
            sysUser = sysUserMapper.findUserByUsernameAndCompanyId(username,companyId);
        }
        UserInfo userInfo = new UserInfo();
        if(sysUser!=null){
            userInfo.setSysUser(sysUser);
            //设置角色列表  （ID）
            List<SysRole> dealerSysRoles = sysRoleMapper.listRolesByUserIdAndCompanyId(sysUser.getUserId(),sysUser.getCompanyId());
            List<String> roles = dealerSysRoles.stream()
                    .map(SysRole::getRoleCode)
                    .collect(Collectors.toList());
            userInfo.setRoles(ArrayUtil.toArray(roles, String.class));
            //设置权限列表（menu.permission）
            Set<String> permissions = new HashSet<>();
            dealerSysRoles.forEach(dealerRole -> {
                List<String> permissionsByRoleId = sysMenuService.findPermissionsByRoleIdAndCompanyId(dealerRole.getRoleId(),sysUser.getCompanyId());
                permissions.addAll(permissionsByRoleId);
            });
            userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        }
        return userInfo;
    }

    @Override
    public IPage getDeptUserWithRolePage(Page page,String companyId,String deptId) {
        return sysUserMapper.getDeptUserPage(page,companyId,deptId);
    }

    @Override
    public IPage getAllUserWithRolePage(Page page, UserVo userVo,String companyId) {
        return sysUserMapper.getAllUserPage(page,userVo,companyId);
    }

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    @Override
    public SysUser getByUserIdAndCompanyId(String userId,String companyId) {
        return sysUserMapper.getByUserIdAndCompanyId(userId,companyId);
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

        if (sysUser != null && !sysUser.getUserId().equals(user.getUserId()))
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

        if (sysUser != null && !sysUser.getUserId().equals(user.getUserId()))
        {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return Boolean.TRUE;
    }
    /**
     * 重置用户密码
     *
     * @return 结果
     */
    @Override
    public Boolean resetUserPwd(String userName,String passWord,String companyId)
    {
        return sysUserMapper.resetUserPwd(userName,ENCODER.encode(passWord),companyId);
    }
    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public Boolean deleteUserByIdsAndCompanyId(String[] userIds,String companyId)
    {
        for (String userId : userIds)
        {
            checkUserAllowed(sysUserMapper.getByUserIdAndCompanyId(userId,companyId));
        }
        return sysUserMapper.deleteUserByIds(userIds,companyId);
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
    public List<SysUser> listUser(String companyId) {
        return sysUserMapper.listUser(companyId);
    }
    /**
     * 根据权限获取用户列表
     * @param deptId
     * @return
     */
    @Override
    public List<SysUser> listUserByDept(String deptId,String companyId) {
        return sysUserMapper.listUserByDept(deptId,companyId);
    }

    /**
     * 根据权限获取用户列表
     * @param post
     * @return
     */
    @Override
    public List<SysUser> listUserByPost(String[] post,String companyId) {
        return sysUserMapper.listUserByPost(post,companyId);
    }

    /**
     * 根据权限获取用户列表
     * @param perms
     * @return
     */
    @Override
    public List<SysUser> listUserByPerms(String[] perms,String companyId) {
        return sysUserMapper.listUserByPerms(perms,companyId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
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
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = sysPostMapper.selectPostsByUserName(userName);
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
