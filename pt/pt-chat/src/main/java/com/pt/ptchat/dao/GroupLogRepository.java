package com.pt.ptchat.dao;

import com.pt.ptchat.entity.GroupLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupLogRepository extends JpaRepository<GroupLog,String> {
    //通过记录ID查询
    GroupLog findGroupLogByGroupLogId(String id);
    //通过群聊ID查询
    List<GroupLog> findGroupLogByChatGroup_ChatId(String chatsId);
}
