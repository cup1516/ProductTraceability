package com.pt.ptchat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//群聊表
@Entity

public class ChatGroup {

    @Id
    @GenericGenerator(name = "jpa-uuid",strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String chatId;//群号

    private String chatName;//群名
    private String chatAvatar;//群头像

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间


    @JsonIgnoreProperties("chatGroup")//
    @OneToMany(mappedBy = "chatGroup",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<GroupLog> groupLogList;//聊天记录

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatAvatar() {
        return chatAvatar;
    }

    public void setChatAvatar(String chatAvatar) {
        this.chatAvatar = chatAvatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    //    public List<User> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<User> userList) {
//        this.userList = userList;
//    }

    public List<GroupLog> getGroupLogList() {
        return groupLogList;
    }

    public void setGroupLogList(List<GroupLog> groupLogList) {
        this.groupLogList = groupLogList;
    }

    @Override
    public String toString() {
        return "ChatGroup{" +
                "chatId='" + chatId + '\'' +
                ", chatName='" + chatName + '\'' +
                ", chatAvatar='" + chatAvatar + '\'' +
                ", createTime=" + createTime +
                ", groupLogList=" + groupLogList +
                '}';
    }
}
