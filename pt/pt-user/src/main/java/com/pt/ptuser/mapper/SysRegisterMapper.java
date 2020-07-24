package com.pt.ptuser.mapper;

import com.pt.ptuser.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author wl
 */
@Mapper
public interface SysRegisterMapper  {



    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Boolean insertUser(@Param("user") SysUser user,@Param("companyId") String companyId);
    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    SysUser checkUserNameUnique(@Param("userName")String userName,@Param("companyId")String companyId);

    /**
     * 校验手机号码是否唯一
     *
     * @return 结果
     */
    SysUser checkPhoneUnique(@Param("phone")String phone,@Param("companyId")String companyId);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    SysUser checkEmailUnique(@Param("email")String email,@Param("companyId")String companyId);

}