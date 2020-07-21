package com.pt.ptuser.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
public class SysUser  {


    @TableId(value = "user_id",type = IdType.INPUT)
    private String userId;

    private String deptId;

    private String companyId;

    private String userName;

    private String nickName;

    private String password;

    private String email;

    private String phone;

    private char sex;

    private String avatar;

    private char status;

    private String[] roleIds;
    private String[] postIds;
    /**
     * 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建时间
     */
//    @ApiModelProperty(value="创建时间")
    private Date createTime;
}