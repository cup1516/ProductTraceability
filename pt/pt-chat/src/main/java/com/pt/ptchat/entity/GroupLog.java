package com.pt.ptchat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

//群聊天记录
@Entity
public class GroupLog {
    @Id
    @GenericGenerator(name = "jpa-uuid",strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String groupLogId;//群消息id

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "chat_id")
    private ChatGroup chatGroup;//群id 外

    /////
    private String sender;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;//发送时间

    private int messageType;//消息类型 0-文字 1-图片 2-语音 3-文件
    private String sendContent;//发送内容

    public String getGroupLogId() {
        return groupLogId;
    }

    public GroupLog setGroupLogId(String groupLogId) {
        this.groupLogId = groupLogId;
        return this;
    }

    public ChatGroup getChatGroup() {
        return chatGroup;
    }

    public GroupLog setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public GroupLog setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public GroupLog setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public GroupLog setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getSendContent() {
        return sendContent;
    }

    public GroupLog setSendContent(String sendContent) {
        this.sendContent = sendContent;
        return this;
    }
    public GroupLog() {
    }

    @Override
    public String toString() {
        return "GroupLog{" +
                "groupLogId='" + groupLogId + '\'' +
                ", chatGroup=" + chatGroup +
                ", sender='" + sender + '\'' +
                ", sendTime=" + sendTime +
                ", messageType=" + messageType +
                ", sendContent='" + sendContent + '\'' +
                '}';
    }
}
