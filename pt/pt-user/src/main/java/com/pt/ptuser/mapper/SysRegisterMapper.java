package com.pt.ptuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


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
    Boolean insertUser(@Param("user") SysUser user,@Param("clientId") String clientId);
    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    SysUser checkUserNameUnique(@Param("userName")String userName,@Param("clientId")String clientId);

    /**
     * 校验手机号码是否唯一
     *
     * @return 结果
     */
    SysUser checkPhoneUnique(@Param("phone")String phone,@Param("clientId")String clientId);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    SysUser checkEmailUnique(@Param("email")String email,@Param("clientId")String clientId);

}