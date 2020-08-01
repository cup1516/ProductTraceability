package com.pt.ptchat.service;

import com.pt.ptchat.dao.ChatGroupRepository;
import com.pt.ptchat.entity.ChatGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 群聊
 */
@Service
public class ChatGroupService {
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    public int save(ChatGroup chatGroup){
        chatGroupRepository.save(chatGroup);
        return 0;
    }

    //根据id查询群信息
    public ChatGroup findChatGroupByChatId(String chatId){
        return chatGroupRepository.findChatGroupByChatId(chatId);
    }

    //根据name查询群信息
    public ChatGroup findChatGroupByChatName(String chatName){
        return chatGroupRepository.findChatGroupByChatName(chatName);
    }
}
