import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/workType/list',
    method: 'get',
    params: query
  })
}


export function create(data) {
  return request({
    url: '/manor/planting/workType/add',
    method: 'post',
    data: data
  })
}


export function isDelete(data) {
  return request({
    url: '/manor/planting/workType/delete',
    method: 'post',
    data: data
  })
}