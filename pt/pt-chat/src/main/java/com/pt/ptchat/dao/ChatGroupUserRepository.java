package com.pt.ptchat.dao;


import com.pt.ptchat.entity.ChatGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatGroupUserRepository extends JpaRepository<ChatGroupUser,String> {
    //通过chatId查询
    List<ChatGroupUser> findChatGroupUsersByChatId (String chatId);
    //通过userId查询
    List<ChatGroupUser> findChatGroupUsersByUserId(String userId);
    //通过companyId查询
    List<ChatGroupUser> findChatGroupUsersByCompanyId(String companyId);
    ////通过userId和companyId查询
    List<ChatGroupUser> findChatGroupUsersByUserIdAndAndCompanyId(String userId,String companyId);
    //通过userId删除
    void deleteChatGroupUserByUserId(String userId);

}
