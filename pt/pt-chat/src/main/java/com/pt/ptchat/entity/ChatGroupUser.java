package com.pt.ptchat.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//群聊用户关联表
@Data
@Entity
public class ChatGroupUser {

    @Id
    @GenericGenerator(name = "jpa-uuid",strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String Id;

    private String chatId;//群号
    private String userId;//用户ID
    private String companyId;//公司ID
}
