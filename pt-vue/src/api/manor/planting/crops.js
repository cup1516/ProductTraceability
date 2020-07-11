import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/planting/crops/list',
    method: 'get',
    params: query
  })
}


export function create(data) {
  return request({
    url: '/manor/planting/crops/add',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/crops/delete',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/manor/planting/crops/update',
    method: 'post',
    data: data
  })
}

