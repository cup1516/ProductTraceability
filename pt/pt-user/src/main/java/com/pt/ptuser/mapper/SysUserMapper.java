package com.pt.ptuser.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptuser.dto.UserDto;
import com.pt.ptuser.entity.SysUser;
import com.pt.ptuser.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysUserMapper  {

    /**
     * 根据username与url查找用户
     * @param username 账号
     * @param url 客户端
     * @return
     */
    UserDto findUserByUsernameAndUrl(@Param("username") String username, @Param("url") String url);

    /**
     * 根据username与url查找用户
     * @param username 账号
     * @param companyId 公司Id
     * @return
     */
    UserDto findUserByUsernameAndCompanyId(@Param("username") String username,@Param("companyId") String companyId);
    /**
     * 分页获取本部门全部用户
     * @param page 分页参数
     * @param deptId
     * @return
     */
    IPage<List<SysUser>> getDeptUserPage(Page page,@Param("companyId") String companyId,@Param("deptId") String deptId);

    /**
     * 分页获取全部用户
     * @param page 分页参数
     * @return
     */
    IPage<List<UserVo>> getAllUserPage(Page page,@Param("user") UserVo user,@Param("companyId") String companyId);

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    SysUser getByUserIdAndCompanyId(@Param("userId") String userId,@Param("companyId") String companyId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
     Boolean updateUser(@Param("user") SysUser user,@Param("companyId") String companyId);

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
    SysUser checkUserNameUnique(@Param("userName") String userName,@Param("companyId") String companyId);

    /**
     * 校验手机号码是否唯一
     *
     * @param phone 手机号码
     * @return 结果
     */
    SysUser checkPhoneUnique(@Param("phone") String phone,@Param("companyId") String companyId);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    SysUser checkEmailUnique(@Param("email") String email,@Param("companyId") String companyId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    Boolean deleteUserById(@Param("userId") String userId,@Param("companyId") String companyId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    Boolean deleteUserByIds(@Param("userIds") String[] userIds,@Param("companyId")String companyId);

    /**
     * 重置用户密码
     *
     * @return 结果
     */
    Boolean resetUserPwd(@Param("username") String username,@Param("password")String password,@Param("companyId")String companyId);
    /**
     * 获取用户列表
     * @return
     */
    List<SysUser> listUser(@Param("companyId")String companyId);
    /**
     * 获取部门用户列表
     * @param deptId
     * @return
     */
    List<SysUser> listUserByDept(@Param("deptId") String deptId,@Param("companyId")String companyId);
    /**
     * 根据职位获取用户列表
     * @param post
     * @return
     */
    List<SysUser> listUserByPost(@Param("post")String[] post,@Param("companyId")String companyId);
    /**
     * 根据权限获取用户列表
     * @param perms
     * @return
     */
    List<SysUser> listUserByPerms( @Param("perms")String[] perms,@Param("companyId")String companyId);
}