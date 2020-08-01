package com.pt.ptcommoncore.security;



import lombok.Data;

import java.util.Date;


@Data
public class SysUser {


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
    private String remark;
    private char status;

    private String[] roleIds;
    private String[] postIds;

    private String delFlag;


    private Date createTime;
}