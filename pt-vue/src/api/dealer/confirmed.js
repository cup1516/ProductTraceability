import request from '@/utils/request'

// 查询节点列表
export function listOrder(query) {
    return request({
      url: '/order/confirmed/page',
      method: 'get',
      params: query
    })
  }
  

  // 修改审查状态
  export function ListCompany(query) {
    return request({
      url: '/company_id/company_id/findCompanyIdByNameLike/'+query,
      method: 'get',
    })
  }