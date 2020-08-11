import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/invalidOrder/list',
    method: 'get',
    params: query
  })
}


export function find(data) {
  return request({
    url: '/manor/invalidOrder/findByMany',
    method: 'post',
    data: data
  })
}