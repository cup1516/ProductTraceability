package com.pt.ptchat.utils;

import lombok.Data;

/**
 * 群聊
 */
@Data
public class MsgVO {
    //id
    //private Integer userId;
    //name
    private String username;
    //头像
    //private String avatar;
    //message
    private String msg;
    //在线人数
    private int count;
    private int type;
}
