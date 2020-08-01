import request from '@/utils/request'

//获取用户聊天列表ByUserId
export function getChatListInfo(query){
  return request({
    url: '/chat/chatgroup/findGroupByUserId',
    method: 'get',
    params: query
  })
}
//获取聊天记录ByGroupId
export function getGroupLog(query){
  return request({
    url: '/chat/chatgroup/findChatlogByGroupId',
    method: 'get',
    params: query
  })
}
//获取小组信息ByGroupId
export function getGroupInfo(query){
  return request({
    url: '/chat/chatgroup/findGroupById',
    method: 'get',
    params: query
  })
}
//创建小组
export function CreateGroup(query){
  return request({
    url: '/chat/chatgroup/createRoom',
    method: 'post',
    data: query
  })
}
//查看某组成员信息
export function ShowGroupMemberInfo(query){
  return request({
    url: '/chat/chatgroup/findUserByGroupId',
    method: 'get',
    params: query
  })
}
//修改小组名
export function ModifyGroupName(query){
  return request({
    url: '/chat/chatgroup/ModifyGroupName',
    method: 'get',
    params: query
  })
}
//为群添加新的用户
export function AddGroupMember(query){
  return request({
    url: '/chat/chatgroup/AddUsers',
    method: 'get',
    params: query
  })
}
//文件传输
export function fileUpload(query){
  return request({
    url: '/chat/api/fileUpload',
    method: 'post',
    data: query,
    headers: {
      'Content-Type': 'multipart/form-data',
    }
  })
}

//文件下载
export function fileDownload(query){
  return request({
    url: '/chat/api/fileDownload',
    method: 'get',
    params: query,
    headers: {
      'Content-Type': 'application/force-download;charset=GBK',
    }
  })
}
