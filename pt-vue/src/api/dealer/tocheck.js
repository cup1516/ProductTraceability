import request from '@/utils/request'

// 查询节点列表
export function listOrder(query) {
    return request({
      url: '/order/tocheck/page',
      method: 'get',
      params: query
    })
  }
  
// 修改审查状态
export function changeCheckStatus(data) {
  return request({
    url: '/order/tocheck/changeCheckStatus',
    method: 'put',
    data: data
  })
}
