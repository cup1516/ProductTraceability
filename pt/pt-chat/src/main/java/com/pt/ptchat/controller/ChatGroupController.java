package com.pt.ptchat.controller;

import com.pt.ptchat.entity.ChatGroup;
import com.pt.ptchat.entity.ChatGroupUser;
import com.pt.ptchat.entity.GroupLog;
import com.pt.ptchat.service.ChatGroupService;
import com.pt.ptchat.service.ChatGroupUserService;
import com.pt.ptchat.utils.code.CommonResult;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chatgroup")
public class ChatGroupController {
    @Autowired
    private ChatGroupService chatGroupService;
    @Autowired
    private ChatGroupUserService chatGroupUserService;

    @PostMapping("/add")
    public CommonResult save(@RequestBody ChatGroup chatGroup){
        if(chatGroupService.save(chatGroup)==0){
            return CommonResult.success(chatGroup);
        }else {
            return CommonResult.failed("添加失败");
        }
    }

    //根据id查找聊天
    @GetMapping("/findGroupById")
    @ResponseBody
    public CommonResult findChatGroupByChatId(String chatId){
        ChatGroup chatGroup = chatGroupService.findChatGroupByChatId(chatId);
        return CommonResult.success(chatGroup);
    }

    //通过userID查找聊天
    @GetMapping("/findGroupByUserId")
    @ResponseBody
    public CommonResult findByUserId(String userId){
        List<ChatGroup> chatGroups = new ArrayList<>();
        //查找关系
        List<ChatGroupUser> chatGroupUserList = chatGroupUserService.findChatGroupUsersByUserId(userId,SecurityUtils.getCompanyId());
        int length = chatGroupUserList.size();
        if(length <= 0){
            return CommonResult.failed("没有群组信息");
        }else {
            for(int i=0;i<length;i++){
                chatGroups.add(chatGroupService.findChatGroupByChatId(chatGroupUserList.get(i).getChatId()));
            }
            return CommonResult.success(chatGroups);
        }
    }

//    //获取用户聊天列表信息
//    @PostMapping("/getChatListInfo")
//    @ResponseBody
//    public CommonResult getChatListInfo(String userId){
//        List<ChatGroupUser> chatGroupUserList = chatGroupUserService.findChatGroupUsersByUserId(userId);
//        List<ChatGroup> chatGroupList = new ArrayList<>();
//        for(int i = 0;i < chatGroupUserList.size();i++){
//            ChatGroup chatGroup = chatGroupService.findChatGroupByChatId(chatGroupUserList.get(i).getChatId());
//            chatGroupList.add(chatGroup);
//        }
//        return CommonResult.success(chatGroupList);
//    }

    //查找聊天记录
    @GetMapping("/findChatlogByGroupId")
    public CommonResult findGroupLog(String chatId,int page){
        ChatGroup chatGroup = chatGroupService.findChatGroupByChatId(chatId);
//        Example<GroupLog> example = Example.of(new GroupLog().setChatGroup(chatGroup), ExampleMatcher.matching().withIgnorePaths("messageType"));
//        Page<GroupLog> groupLogs = groupLogRepository.findAll(example, PageRequest.of(Integer.parseInt(page),5, Sort.Direction.DESC,"sendTime"));
        List<GroupLog> groupLogList= chatGroup.getGroupLogList();
        List<Map<String,Object>> myLog = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> log = new ArrayList<Map<String,Object>>();

        for(int i=0;i<groupLogList.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();
            GroupLog grouplog = groupLogList.get(i);
            Integer messageType = grouplog.getMessageType();
            Date sendTime = grouplog.getSendTime();
            String senderId = grouplog.getSender();
            String sendContent = grouplog.getSendContent();
/*
            SysUser user =sySUserService.getByUserId(senderId);
            String nickName = user.getNickName();
            String userName = user.getUserName();
            String avatar = user.getAvatar();
 */
//            User user = userService.getUser(senderId);
//            String nickName = user.getNickName();
//            String userName = user.getUserName();
//            String avatar = user.getAvatar();

            map.put("messageType",messageType);
            map.put("senderId",senderId);
//            map.put("avatar",avatar);
//            map.put("userName",userName);
//            map.put("nickName",nickName);
            map.put("sendContent",sendContent);
            map.put("sendTime",sendTime);
            myLog.add(map);
        }
        if(!myLog.isEmpty()){
            //排序
            Collections.sort(myLog, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return o1.get("sendTime").toString().compareTo(o2.get("sendTime").toString());
                }
            });
            Collections.reverse(myLog);
            //分页
            int row = 5;
            int size = myLog.size();
            int pageStart = page==1?0:(page-1)*row;
            int pageEnd = size<page*row?size:page*row;
            int totalPage;
            if(size>pageStart){
                log =myLog.subList(pageStart, pageEnd);
            }
            //总页数
            if(myLog.size()%row==0){
                totalPage=myLog.size()/row;
            }else {
                totalPage = myLog.size()/row+1;
            }
            if(page>totalPage) return CommonResult.failed("参数超标");
            return CommonResult.success(log,Integer.toString(totalPage));
        }else {
            return CommonResult.failed("没有聊天记录");
        }
    }

    //创建房间，拉人
    @PostMapping("/createRoom")
    @ResponseBody
    public CommonResult createRoom(@RequestBody ChatGroup group){
        ChatGroup chatGroup = new ChatGroup();
        Date date = new Date();
        String avatar = group.getChatAvatar();
        String name = group.getChatName();
        String id = group.getChatId();

        String aa="";
        String regeX="[\\[\\]]";
        String userIds = id.replaceAll(regeX,aa);

        String userId[] = userIds.split(",");

        chatGroup.setChatName(name);
        chatGroup.setChatAvatar(avatar);
        chatGroup.setCreateTime(date);
        chatGroupService.save(chatGroup);

        String chatId = chatGroup.getChatId();
        for(int i=0;i<userId.length;i++){
            ChatGroupUser chatGroupUser = new ChatGroupUser();
            chatGroupUser.setChatId(chatId);
            chatGroupUser.setUserId(userId[i].replace("\"",""));
            chatGroupUser.setCompanyId(SecurityUtils.getCompanyId());
            chatGroupUserService.save(chatGroupUser);
        }
        return CommonResult.success(chatGroup);
    }

    //查看小组组员信息
    @GetMapping("/findUserByGroupId")
    public CommonResult findUser(String chatId){
        List<ChatGroupUser> chatGroupUserList = chatGroupUserService.findChatGroupUsersByChatId(chatId);
        List<String> userIdList = new ArrayList<>();
        for(int i=0;i<chatGroupUserList.size();i++){
            userIdList.add(chatGroupUserList.get(i).getUserId());
        }
        return CommonResult.success(userIdList);//userList
    }



    //修改小组名
    @GetMapping("/ModifyGroupName")
    public CommonResult ModifyGroupName(String groupId,String newName){
        ChatGroup chatGroup = chatGroupService.findChatGroupByChatId(groupId);
        chatGroup.setChatName(newName);
        chatGroupService.save(chatGroup);
        return CommonResult.success(chatGroup);
    }

    //为群添加新的用户
    @GetMapping("/AddUsers")
    public CommonResult AddUser(String groupId,String userId){
        String[] userIdList = userId.split(",");
        for(int i=0;i<userIdList.length;i++){
            ChatGroupUser chatGroupUser = new ChatGroupUser();
            chatGroupUser.setChatId(groupId);
            chatGroupUser.setUserId(userIdList[i]);
            chatGroupUser.setCompanyId(SecurityUtils.getCompanyId());
            chatGroupUserService.save(chatGroupUser);
        }

        return CommonResult.success(userIdList);
    }

    //某一用户退出群聊
//    @GetMapping("/DeleteUser")
//    public CommonResult DeleteUser(String groupId,String userId){
//        ChatGroupUser chatGroupUser = new ChatGroupUser();
//        List<ChatGroupUser> chatGroupUserList= chatGroupUserService.findChatGroupUsersByChatId(groupId);
//
//    }
}
