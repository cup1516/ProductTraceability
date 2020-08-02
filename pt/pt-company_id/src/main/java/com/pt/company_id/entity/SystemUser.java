package com.pt.company_id.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author MarkerHub
 * @since 2020-07-24
 */
@Entity
@Data
/*@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)*/
@TableName("system_user")
public class SystemUser {



    /**
     * 用户id
     */
    @Id
    @TableId(value = "user_id",type = IdType.INPUT)
    private String userId;

    @Column(name="company_id")
    private String companyId;

    /**
     * 部门id
     */
    @Column(name="dept_id")
    private String deptId;

    /**
     * 用户账号
     */
    @Column(name="user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @Column(name="nick_name")
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 性别 0-男 1-女
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 状态 0-正常 1-停用
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name="create_time")
    private LocalDateTime createTime;

    /**
     * 删除标志 0-正常 1-已删除
     */
    @Column(name="del_flag")
    private String delFlag;

    private String remark;


}
