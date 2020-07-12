import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/farmlandLocation/list',
    method: 'get',
    params: query
  })
}

export function create(data) {
  return request({
    url: '/manor/planting//farmlandLocation/add',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/farmlandLocation/delete',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/manor/planting/farmlandLocation/update',
    method: 'post',
    data: data
  })
}


export function getParams(id) {
  return request({
    url: '/manor/planting/farmlandLocation/get',
    method: 'get',
    params: id
  })
}

