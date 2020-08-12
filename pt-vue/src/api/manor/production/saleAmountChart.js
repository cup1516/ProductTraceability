import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/saleAmount/chart',
    method: 'get',
    params: query
  })
}

export function getFindList(data) {
  return request({
    url: '/manor/saleAmount/chartFindList',
    method: 'post',
    data: data
  })
}


