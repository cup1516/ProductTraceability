import request from '@/utils/request'

// 查询节点列表
export function listMission(query) {
    return request({
      url: '/proc/mission/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function changeStatus(data,status) {
    data.status = status
    return request({
      url: '/proc/mission/changeStatus',
      method: 'put',
      data: data
    })
  }
  
  // 新增节点
  export function addMission(data) {
    return request({
      url: '/proc/mission',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateMission(data) {
    return request({
      url: '/proc/mission',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delMission(postId) {
    return request({
      url: '/proc/mission/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/proc/mission/list',
    method: 'get',
  })
}