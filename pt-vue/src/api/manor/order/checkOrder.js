import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/checkOrder/list',
    method: 'get',
    params: query
  })
}

export function pass(data) {
  return request({
    url: '/manor/checkOrder/pass',
    method: 'post',
    data: data
  })
}

export function checkBack(data) {
  return request({
    url: '/manor/checkOrder/checkBack',
    method: 'post',
    data: data
  })
}



export function find(data) {
  return request({
    url: '/manor/checkOrder/findByMany',
    method: 'post',
    data: data
  })
}
