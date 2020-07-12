import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 分页查询用户列表
export function listUser(query) {
  return request({
    url: '/user/user/page',
    method: 'get',
    params: query
  })
}
// 查询用户列表
export function getUserList() {
  return request({
    url: '/user/user/list',
    method: 'get',
  })
}
// 查询本部门人员
export function getUserListDept(query) {
  return request({
    url: '/user/user/list/dept',
    method: 'get',
    params: query
  })
}
// 根据权限查询用户列表
export function getUserListPerms(query) {
  return request({
    url: '/user/user/list/perms/'+query,
    method: 'get',
  })
}
// 根据职位查询用户列表
export function getUserListPost(query) {
  return request({
    url: '/user/user/list/post/'+query,
    method: 'get',
  })
}
// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/user/user/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/user/user',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/user/user',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/user/user/' + userId,
    method: 'delete'
  })
}

// 导出用户
export function exportUser(query) {
  return request({
    url: '/user/user/export',
    method: 'get',
    params: query
  })
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/user/user/resetPwd',
    method: 'put',
    data: data
  })
}
// 用户密码重置
export function getInitUserPwd() {

  return request({
    url: '/user/user/initPwd',
    method: 'get',
  })
}
// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/user/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/user/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/user/user/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/user/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/user/fileUpload',
    method: 'post',
    data: data
  })
}

//修改头像
export function updateAvatar(data) {
  return request({
    url: '/user/user/profile/updateAvatar',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/user/user/importTemplate',
    method: 'get'
  })
}
