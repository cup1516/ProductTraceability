import request from '@/utils/request'

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: '/user/menu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(menuId) {
  return request({
    url: '/user/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
export function treeselect() {
  return request({
    url: '/user/menu/tree',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/user/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: '/user/menu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/user/menu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(menuId,type,clientType) {
  const data = {
    menuId,
    type,
    clientType
  }
  return request({
    url: '/user/menu' ,
    method: 'delete',
    data: data
  })
}
// 获取路由
export function getRouters() {
  return request({
    url: '/user/menu/routers',
    method: 'get'
  })
}