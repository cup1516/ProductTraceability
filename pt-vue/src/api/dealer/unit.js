import request from '@/utils/request'

// 查询节点列表
export function listUnit(query) {
    return request({
      url: '/prod/unit/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getUnit(unitId) {
    return request({
      url: '/prod/unit/' + unitId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addUnit(data) {
    return request({
      url: '/prod/unit',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateUnit(data) {
    return request({
      url: '/prod/unit',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delUnit(postId) {
    return request({
      url: '/prod/unit/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/prod/unit/list',
    method: 'get',
  })
}