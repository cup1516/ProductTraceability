import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/finalInvoice/list',
    method: 'get',
    params: query
  })
}


export function find(data) {
  return request({
    url: '/manor/finalInvoice/findByMany',
    method: 'post',
    data: data
  })
}


