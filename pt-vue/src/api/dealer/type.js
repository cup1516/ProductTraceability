import request from '@/utils/request'

// 查询节点列表
export function listType(query) {
    return request({
      url: '/prod/type/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getType(typeId) {
    return request({
      url: '/prod/type/' + typeId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addType(data) {
    return request({
      url: '/prod/type',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateType(data) {
    return request({
      url: '/prod/type',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delType(postId) {
    return request({
      url: '/prod/type/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/prod/type/list',
    method: 'get',
  })
}