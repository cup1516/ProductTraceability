package com.pt.ptchat.dao;

import com.pt.ptchat.entity.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup,String> {
    //通过ID查询
    ChatGroup findChatGroupByChatId(String chatId);
    //通过chatId查询
    ChatGroup findChatGroupByChatName(String chatName);
}
