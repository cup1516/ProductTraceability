import request from '@/utils/request'

// 查询节点列表
export function listOrder(query) {
    return request({
      url: '/order/order/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getOrder(orderId) {
    return request({
      url: '/order/order/' + orderId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addOrder(data) {
    return request({
      url: '/order/order',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updateOrder(data) {
    return request({
      url: '/order/order',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delOrder(postId) {
    return request({
      url: '/order/order/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/order/order/list',
    method: 'get',
  })
}