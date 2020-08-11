import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/order/myList',
    method: 'get',
    params: query
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/order/delete',
    method: 'post',
    data:data
  })
}

export function toReview(data) {
  return request({
    url: '/manor/order/toReview',
    method: 'post',
    data:data
  })
}

export function backToReview(data) {
  return request({
    url: '/manor/order/backToReview',
    method: 'post',
    data:data
  })
}

export function ListCompany(query) {
  return request({
    url: '/company_id/company_id/findCompanyIdByNameLike/'+query,
    method: 'get',
  })
}

export function find(data) {
  return request({
    url: '/manor/order/findByMany',
    method: 'post',
    data: data
  })
}






