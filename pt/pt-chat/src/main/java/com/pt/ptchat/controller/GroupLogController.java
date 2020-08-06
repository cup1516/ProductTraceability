package com.pt.ptchat.controller;

import com.pt.ptchat.entity.GroupLog;
import com.pt.ptchat.service.GroupLogService;
import com.pt.ptchat.utils.code.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupLog")
public class GroupLogController {
    @Autowired
    private GroupLogService groupLogService;

    @GetMapping("/grouplogs")
    public List<GroupLog> findGroupLog(String id){
        return groupLogService.findGroupLogByChatgroupId(id);
    }

}
