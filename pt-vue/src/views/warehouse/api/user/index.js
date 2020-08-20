import request from '@/utils/request'

/* ------------------------------------登入的接口------------------------------------ */
export function login (data) {
  const url = '/permission-service/login'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------分页查询所有的接口------------------------------------ */

export function findRolesByUserId (data) {
  return request({
    url: '/permission-service/user/find_user_roles_by_user_id?userId=' + data,
    method: 'get'
  })
}

/* ------------------------------------分页查询所有的接口------------------------------------ */

/* ------------------------------------校验验证码的接口------------------------------------ */

export function checkVerificationCode (data) {
  const url = '/permission-service/user/check_verification_code'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ---------------------------------构建菜单树的接口 -------------------------------------- */

export function getMenuConfig () {
  return request({
    url: '/permission-servicemenu/build_menu_tree',
    method: 'get'
  })
}

/* ---------------------------------账号登出的接口------------------------------------------- */
export function logout () {
  return request({
    url: '/permission-service/user/logout',
    method: 'get'
  })
}

/* --------------------------------分页查询所有用户信息 --------------------------------------- */
export function findAllUserByPage (data) {
  const url = '/permission-service/user/find_all_user_by_page?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&username=' + data.username + '&userStyle=' + data.userStyle + '&sex=' + data.sex + '&phone=' + data.phone
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除用户的接口------------------------------------ */
export function deleteUsersByPrimaryKeys (data) {
  const url = '/permission-service/user/delete_users_by_user_ids?userIds=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------新增用户的接口------------------------------------ */
export function insertSysUser (data) {
  const url = '/permission-service/user/save_sys_user'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------更新用户的接口------------------------------------ */
export function updateSysUserByPrimaryKey (data) {
  const url = '/permission-service/user/save_sys_user'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据角色ID获取菜单接口------------------------------------ */
export function findMenusByRoleId (data) {
  const url = '/permission-service/menu/build_menu_tree_by_role_id?roleId=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* -------------------------------------给指定的用户分配角色 ------------------------------------- */
export function assignRolesByUserId (data) {
  const url = '/permission-service/user/save_user_roles?userId=' + data.userId + '&roleIds=' + data.roleIds
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function findAllApprovers (approver) {
  const url = '/user/user/list1/perms/'+approver
  return request.get(url).then(res => Promise.resolve(res.data))
}
/* ------------------------------------- 获取加密公钥信息 ------------------------------------- */
export function getPublicSecret (data) {
  const url = '/permission-service/user/get_public_secret?privateKeyId=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}
