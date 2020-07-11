import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/planting/material/list',
    method: 'get',
    params: query
  })
}