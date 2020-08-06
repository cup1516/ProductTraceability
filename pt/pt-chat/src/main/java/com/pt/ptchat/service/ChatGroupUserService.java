package com.pt.ptchat.service;

import com.pt.ptchat.dao.ChatGroupUserRepository;
import com.pt.ptchat.entity.ChatGroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群聊用户关联表
 */
@Service
public class ChatGroupUserService {
    @Autowired
    private ChatGroupUserRepository chatGroupUserRepository;

    public int save(ChatGroupUser chatGroupUser){
        chatGroupUserRepository.save(chatGroupUser);
        return 0;
    }

    //通过chatId查询
    public List<ChatGroupUser> findChatGroupUsersByChatId(String chatId){
        return chatGroupUserRepository.findChatGroupUsersByChatId(chatId);
    }
    //通过userId查询
    public List<ChatGroupUser> findChatGroupUsersByUserId(String userId,String companyId){

        return chatGroupUserRepository.findChatGroupUsersByUserIdAndAndCompanyId(userId,companyId);
    }
}
