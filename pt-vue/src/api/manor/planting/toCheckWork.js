import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/work/toCheckList',
    method: 'get',
    params: query
  })
}

export function find(data) {
  return request({
    url: '/manor/planting/myWork/toCheckWorkFindByMany',
    method: 'post',
    data: data
  })
}

export function pass(data) {
  return request({
    url: '/manor/planting/myWork/pass',
    method: 'post',
    data: data
  })
}


export function checkBack(data) {
  return request({
    url: '/manor/planting/myWork/checkBack',
    method: 'post',
    data: data
  })
}



