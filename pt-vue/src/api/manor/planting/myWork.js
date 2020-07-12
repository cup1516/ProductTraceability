import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/myWork/list',
    method: 'get',
    params: query
  })
}

export function find(data) {
  return request({
    url: '/manor/planting/myWork/myWorkFindByMany',
    method: 'post',
    data: data
  })
}