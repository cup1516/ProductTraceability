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


export function toCheck(data) {
  return request({
    url: '/manor/planting/myWork/toCheck',
    method: 'post',
    data: data
  })
}

export function backFromCheck(data) {
  return request({
    url: '/manor/planting/myWork/backFromCheck',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/myWork/delete',
    method: 'post',
    data: data
  })
}
