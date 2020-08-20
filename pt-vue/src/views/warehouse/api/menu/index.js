import request from '@/utils/request'
export function findMenuListByPage (data) {
  if (data.menuName == null || data.menuName === undefined) {
    data.menuName = ''
  }
  const url = '/permission-service/menu/find_all_menus_by_page?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&menuName=' + data.menuName
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function deleteMenusByPrimaryKeys (data) {
  const url = '/permission-service/menu/delete_menus_by_menu_ids?menuIds=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* -------------------------------------保存菜单------------------------------- */
export function saveMenu (data) {
  const url = '/permission-service/menu/save_menu'
  return request.post(url, data)
}

/* -------------------------------------给指定的菜单分配角色 -------------------------------- */
export function AssignRolesByMenuId (data) {
  const url = '/permission-service/menu/save_menu_roles?menuId=' + data.menuId + '&roleIds=' + data.roleIds
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* -------------------------------------根据菜单ID查詢角色列表-------------- */
export function getSelectRoleList (data) {
  const url = '/permission-service/menu/find_menu_roles_by_menu_id?menuId=' + data.menuId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------构建菜单树------------------------------------- */
export function buildMenuTree () {
  const url = '/permission-service/menu/build_menu_tree'
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------构建菜单树------------------------------------- */
export function findMenuTree () {
  const url = '/permission-service/menu/build_menu_tree'
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ---------------批量删除菜单----------------------- */
export function batchDelete () {
  return null
}
