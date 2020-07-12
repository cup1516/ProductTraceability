import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/farmlandRegionType/list',
    method: 'get',
    params: query
  })
}

export function create(data) {
  return request({
    url: '/manor/planting/farmlandRegionType/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/manor/planting/farmlandRegionType/update',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/farmlandRegionType/delete',
    method: 'post',
    data: data
  })
}