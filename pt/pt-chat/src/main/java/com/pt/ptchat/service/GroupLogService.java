package com.pt.ptchat.service;

import com.pt.ptchat.dao.GroupLogRepository;
import com.pt.ptchat.entity.GroupLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupLogService {
    @Autowired
    private GroupLogRepository groupLogRepository;

    public int save(GroupLog groupLog){
        groupLogRepository.save(groupLog);
        return 0;
    }

    //通过房间ID查询
    public List<GroupLog> findGroupLogByChatgroupId(String chatId){
        return groupLogRepository.findGroupLogByChatGroup_ChatId(chatId);
    }
}
