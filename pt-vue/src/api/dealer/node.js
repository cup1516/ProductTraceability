import request from '@/utils/request'

// 查询节点列表
export function listNode(query) {
    return request({
      url: '/proc/node/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getNode(nodeId) {
    return request({
      url: '/proc/node/' + nodeId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addNode(data) {
    return request({
      url: '/proc/node',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateNode(data) {
    return request({
      url: '/proc/node',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delNode(postId) {
    return request({
      url: '/proc/node/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/proc/node/list',
    method: 'get',
  })
}