import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/financaial/list',
    method: 'get',
    params: query
  })
}

export function find(data) {
  return request({
    url: '/manor/financaial/find',
    method: 'post',
    data: data
  })
}
export function getParams (id) {
  return request({
    url: '/manor/invoice/get',
    method: 'get',
    params: id
  })
}
