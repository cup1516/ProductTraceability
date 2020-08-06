package com.pt.ptuser.dto;


import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class UserDto {


    private String userId;

    private String deptId;

    private String companyId;
    private String companyName;
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
    /**
     * 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}