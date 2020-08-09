import request from '@/utils/request'

// 查询节点列表
export function listOrder(query) {
    return request({
      url: '/order/myorder/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getOrder(orderId) {
    return request({
      url: '/order/myorder/' + orderId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addOrder(data) {
    return request({
      url: '/order/myorder',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateOrder(data) {
    return request({
      url: '/order/myorder',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delOrder(postId) {
    return request({
      url: '/order/myorder/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/order/myorder/list',
    method: 'get',
  })
}
// 修改审查状态
export function changeCheckFlag(data) {
  return request({
    url: '/order/myorder/changeCheckFlag',
    method: 'put',
    data: data
  })
}
  // 修改审查状态
  export function ListCompany(query) {
    return request({
      url: '/company_id/company_id/findCompanyIdByNameLike/'+query,
      method: 'get',
    })
  }