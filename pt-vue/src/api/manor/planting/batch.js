import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/planting/batch/list',
    method: 'get',
    params: query
  })
}

export function create(data) {
  return request({
    url: '/manor/planting/batch/add',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/batch/delete',
    method: 'post',
    data: data
  })
}
