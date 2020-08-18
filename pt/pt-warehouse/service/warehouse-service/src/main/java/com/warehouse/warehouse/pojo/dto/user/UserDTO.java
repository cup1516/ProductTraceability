package com.warehouse.warehouse.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: UserDTO
 *
 * @Author: wanpp
 * @Date: 2020/4/21 20:35
 * Description: 用户
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 4404281471828783126L;

    private String userId;

    private String username;

    private String salt;

    private String userStyle;

    private String nickName;

    private Integer sex;

    private String phone;

    private String email;

    private String createUserId;

    private String creator;

    private String updateUserId;

    private String updator;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    private String companyId;

}
