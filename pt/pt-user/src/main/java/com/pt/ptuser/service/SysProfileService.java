package com.pt.ptuser.service;

import com.pt.ptuser.entity.SysUser;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author wl
 */
public interface SysProfileService {
    Boolean updateUserProfile(SysUser sysUser);

    Boolean updatePwd(String oldPassword, String newPassword);

    Map<String, String> profile() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
