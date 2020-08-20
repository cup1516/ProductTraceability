import request from '@/utils/request'

/* --------------------------------------获取角色列表------------------------- */
export function getRoleList () {
  const url = '/permission-service/role/find_all_roles'
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* -------------------------------------根据用戶ID查詢角色列表-------------- */
export function getSelectRoleList (data) {
  const url = '/permission-service/user/find_user_roles_by_user_id?userId=' + data.userId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ----------------------------------------分页查询所有角色列表----------------- */
export function findRoleListByPage (data) {
  const url = '/permission-service/role/find_all_roles_by_page?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&roleName=' + data.roleName
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function deleteRoleByPrimaryKeys (data) {
  const url = '/permission-service/role/delete_roles_by_role_ids?roleIds=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* -------------------------------------保存角色------------------------------- */
export function saveRole (data) {
  const url = '/permission-service/role/save_sys_role'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
