import request from '@/utils/request'

// 查询流程列表
export function listProcess(query) {
    return request({
      url: '/proc/process/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询流程详细
  export function getProcess(processId) {
    return request({
      url: '/proc/process/' + processId,
      method: 'get'
    })
  }
  
  // 新增流程
  export function addProcess(data) {
    return request({
      url: '/proc/process',
      method: 'post',
      data: data
    })
  }
  
  // 修改流程
  export function updateProcess(data) {
    return request({
      url: '/proc/process',
      method: 'put',
      data: data
    })
  }
  
  // 删除流程
  export function delProcess(processId) {
    return request({
      url: '/proc/process/' + processId,
      method: 'delete'
    })
  }
  
// 修改审查状态
export function changeCheckStatus(data) {
  return request({
    url: '/proc/process/changeCheckStatus',
    method: 'put',
    data: data
  })
}