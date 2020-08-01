package com.pt.ptauth.service;

import cn.hutool.core.util.ArrayUtil;
import com.pt.ptauth.feign.RemotePtUserClient;
import com.pt.ptauth.util.RedisUtils;
import com.pt.ptcommoncore.constant.CacheConstants;
import com.pt.ptcommoncore.constant.SecurityConstants;
import com.pt.ptcommoncore.security.CustomUser;
import com.pt.ptcommoncore.security.SysUser;
import com.pt.ptcommoncore.security.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author wl
 * @date 2020/5/19
 */
@Service
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
//    @Resource
    RedisUtils redisUtils;
//    @Autowired
    RemotePtUserClient remotePtUserClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object clientIdObject = redisUtils.get(CacheConstants.CLIENT_DETAILS_KEY);
        String[] split = username.split("_");
        username = split[0];
        String url;
        if(split.length == 2){
            url = split[1];
        }else{
            url = "";
        }
        UserInfo info = remotePtUserClient.info(username,url);
        if (info.getSysUser() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        CustomUser user = getUserDetails(info);
        return user;
    }
    /**
     * 构建userdetails
     *
     * @param userInfo 用户信息
     * @return
     */
    private CustomUser getUserDetails(UserInfo userInfo) {
        SysUser user = userInfo.getSysUser();
        //添加权限
        Collection<CustomAuthority> customAuthorityCollection = new ArrayList<>();
        if(ArrayUtil.isNotEmpty(userInfo.getRoles())){
            Arrays.stream(userInfo.getRoles()).forEach(role -> {
                customAuthorityCollection.add(new CustomAuthority(SecurityConstants.ROLE + role));
            });
        }
        //构建user对象
        return new CustomUser(user.getUserName(),user.getPassword(),user.getUserId(),user.getUserName(),user.getNickName(),user.getDeptId(),user.getCompanyId(), customAuthorityCollection);
   }
}
