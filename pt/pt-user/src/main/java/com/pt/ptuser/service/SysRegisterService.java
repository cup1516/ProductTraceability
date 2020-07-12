package com.pt.ptuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pt.ptuser.dto.UserInfo;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.vo.UserVo;

import java.util.List;

/**
 * @author wl
 */
public interface SysRegisterService  {



   /**
    * 新增用户信息
    *
    * @param user 用户信息
    * @return 结果
    */
   Boolean insertUser(SysUser user,String clientId);
   /**
    * 校验用户名称是否唯一
    *
    * @param user 用户
    * @return 结果
    */
   Boolean checkUserNameUnique(SysUser user,String clientId);

   /**
    * 校验手机号码是否唯一
    *
    * @param user 用户信息
    * @return 结果
    */
   Boolean checkPhoneUnique(SysUser user,String clientId);

   /**
    * 校验email是否唯一
    *
    * @param user 用户信息
    * @return 结果
    */
   Boolean checkEmailUnique(SysUser user,String clientId);


}
