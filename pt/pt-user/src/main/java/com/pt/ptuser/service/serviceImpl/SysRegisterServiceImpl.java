package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptuser.dto.UserInfo;
import com.pt.ptuser.entity.*;
import com.pt.ptuser.mapper.*;
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
public class SysRegisterServiceImpl  implements SysRegisterService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private SysUserRoleMapper sysUserRoleMapper;
    private SysUserPostMapper sysUserPostMapper;
    private SysRegisterMapper sysRegisterMapper;




    @Override
    public Boolean insertUser(SysUser user,String clientId) {
        user.setUserId(IdUtils.simpleUUID());
        user.setPassword(ENCODER.encode(user.getPassword()));
        insertUserPost(user,clientId);
        insertUserRole(user,clientId);
        return sysRegisterMapper.insertUser(user,clientId);
    }
    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user,String clientId)
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
                sysUserRoleMapper.batchUserRoleWithClientId(list,clientId);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user,String clientId)
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
                sysUserPostMapper.batchUserPostWithClientId(list,clientId);
            }
        }
    }
    @Override
    public Boolean checkUserNameUnique(SysUser user,String clientId) {
        SysUser sysUser = sysRegisterMapper.checkUserNameUnique(user.getUserName(),clientId);
        if(sysUser != null){
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkPhoneUnique(SysUser user,String clientId) {
        if(StrUtil.isEmpty(user.getUserId())){
            return Boolean.TRUE;
        }
        SysUser sysUser = sysRegisterMapper.checkPhoneUnique(user.getPhone(),clientId);

        if (sysUser != null && !sysUser.getUserId().equals(user.getUserId()))
        {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkEmailUnique(SysUser user,String clientId) {
        if(StrUtil.isEmpty(user.getUserId())){
            return Boolean.TRUE;
        }
        SysUser sysUser = sysRegisterMapper.checkEmailUnique(user.getEmail(),clientId);

        if (sysUser != null && !sysUser.getUserId().equals(user.getUserId()))
        {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return Boolean.TRUE;
    }

}
