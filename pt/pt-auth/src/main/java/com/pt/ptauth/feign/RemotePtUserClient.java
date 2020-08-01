package com.pt.ptauth.feign;

import com.pt.ptcommoncore.security.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wl
 */
@FeignClient(name = "pt-user")
public interface RemotePtUserClient {

    /**
     * 获取用户信息
     * @param username
     * @param url
     * @return
     */
    @GetMapping("/user/info/{username}")
    UserInfo info(@PathVariable(value = "username") String username, @RequestParam(value = "url") String url);
}
