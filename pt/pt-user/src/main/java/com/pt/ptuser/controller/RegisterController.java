package com.pt.ptuser.controller;

import com.pt.ptcommoncore.util.R;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.service.SysRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author wl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private SysRegisterService sysRegisterService;

    @PostMapping
    private R register(@RequestBody SysUser user,@RequestParam String clientId){
        sysRegisterService.checkUserNameUnique(user,clientId);
        sysRegisterService.checkPhoneUnique(user,clientId);
        sysRegisterService.checkEmailUnique(user,clientId);
//        user.setCreateBy(SecurityUtils.getUsername());
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return R.ok(sysRegisterService.insertUser(user,clientId));
    }
}
