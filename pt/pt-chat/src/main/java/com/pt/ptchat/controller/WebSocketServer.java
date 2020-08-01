package com.pt.ptchat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pt.ptchat.config.SpringContextHelper;
import com.pt.ptchat.entity.ChatGroup;
import com.pt.ptchat.entity.GroupLog;
import com.pt.ptchat.service.ChatGroupService;
import com.pt.ptchat.service.ChatGroupUserService;
import com.pt.ptchat.service.GroupLogService;
import com.pt.ptchat.utils.MsgVO;

import com.pt.ptuser.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket/chat/{roomId}/{userId}",configurator= SpringContextHelper.class)
public class WebSocketServer {
    private String userId;
    private String roomId;
    private Session session;

    @Autowired
    private ChatGroupService chatGroupService;
    @Autowired
    private ChatGroupUserService chatGroupUserService;
    @Autowired
    private GroupLogService groupLogService;
    @Autowired
    private SysUserService sysUserService;


    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    // private static final Map<String, String> userNameList = new ConcurrentHashMap();
    //房间id和房间名
    private static Map<String,String> roomList = new HashMap<String, String>();
    private static Map<String,List<String>> roomUserList= new HashMap<>();

    @OnOpen
    public void connect(@PathParam("roomId") String roomId, @PathParam("userId") String userId, Session session) throws Exception {
        System.out.println("打开链接");
        this.session = session;
        this.userId = userId;
        this.roomId = roomId;

        Map<String,Object> message=new HashMap<String, Object>();


        //查找群组看是否存在..一定存在
        ChatGroup mygroup = chatGroupService.findChatGroupByChatId(roomId);
        // 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomId)) {
            System.out.println(session);
            // 创建房间不存在时，创建房间
            Set<Session> room = new HashSet<Session>();
            // 添加用户
            room.add(session);
            rooms.put(roomId, room);
            List<String> users = new ArrayList<>();
            users.add(userId);
            roomUserList.put(roomId,users);
            ChatGroup chatGroup = new ChatGroup();
            chatGroup.setChatId(roomId);
            roomList.put(roomId,chatGroup.getChatName());
        } else {
            List<String> userList = roomUserList.get(roomId);
            // 房间已存在，直接添加用户到相应的房间
            if(!userList.contains(userId)){
                roomUserList.get(roomId).add(userId);
                //System.out.println("user:"+userId+"加入房间"+roomId);
            }
            rooms.get(roomId).add(session);
            System.out.println("房间已存在");
        }

        //记录房间人数
       // int count = rooms.get(roomId).size();
        int count = roomUserList.get(roomId).size();
        //System.err.println("username"+username);
        System.out.println(userId+"进入房间"+roomId+",当前房间人数："+count);
        message.put("count",count); //在线人数
        message.put("sender",userId); //Id
        message.put("chatName",roomId); //房间号
        //this.session.getAsyncRemote().sendText(new Gson().toJson(message));
        System.out.println(message);
    }

    @OnClose
    public void disConnect(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session) {
        rooms.get(roomId).remove(session);
        roomUserList.get(roomId).remove(userId);
        System.out.println(userId+"离开房间"+roomId+",当前房间人数："+roomUserList.get(roomId).size());
    }

    @OnMessage
    public void receiveMsg(@PathParam("roomId") String roomId,@PathParam("userId") String userId,
                           String msg, Session session) throws Exception {
        MsgVO socketMsg;
        ObjectMapper objectMapper = new ObjectMapper();
        socketMsg = objectMapper.readValue(msg, MsgVO.class);
        broadcast(roomId, userId,socketMsg.getMsg(),socketMsg.getType());
        System.out.println("来自客户端的消息--> 房间：" + roomId + "," + userId + ": " + msg);
    }

    // 按照房间名进行广播
    public void broadcast(String roomId, String userId,String msg,int type) throws Exception {
        for (Session session : rooms.get(roomId)) {
            Map<String,Object> m=new HashMap<String, Object>();
            int count = roomUserList.get(roomId).size();
            m.put("count",count);
            m.put("sendContent",msg);
            m.put("chatId",roomId); //房间号
            m.put("messageType",type);//消息类型
            m.put("senderId",userId);
            //session.getBasicRemote().sendText(new Gson().toJson(m));
            session.getAsyncRemote().sendText(new Gson().toJson(m));//异步发送消息.message
        }
        if(type == 1 || type == 2 || type == 3 || type == 0){
            //消息存储
            Date day=new Date();
            String rid = roomId;
            ChatGroup mygroup = chatGroupService.findChatGroupByChatId(rid);
            GroupLog grouplog = new GroupLog();
            grouplog.setChatGroup(mygroup);
            grouplog.setSender(userId);
            grouplog.setSendTime(day);
            grouplog.setSendContent(msg);
            grouplog.setMessageType(type);
            groupLogService.save(grouplog);
        }
    }
}
