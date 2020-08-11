import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/planting/material/list',
    method: 'get',
    params: query
  })
}

export function create(data) {
  return request({
    url: '/manor/planting/material/add',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/material/delete',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/manor/planting/material/update',
    method: 'post',
    data: data
  })
}