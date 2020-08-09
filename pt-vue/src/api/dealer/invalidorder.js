import request from '@/utils/request'

// 查询节点列表
export function listOrder(query) {
    return request({
      url: '/order/invalidorder/page',
      method: 'get',
      params: query
    })
  }
  
