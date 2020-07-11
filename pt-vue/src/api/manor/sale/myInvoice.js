import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/invoice/list',
    method: 'get',
    params: query
  })
}


export function toCheck(data) {
  return request({
    url: '/manor/myInvoice/toCheck',
    method: 'post',
    data: data
  })
}

export function backFromCheck(data) {
  return request({
    url: '/manor/myInvoice/backFromCheck',
    method: 'post',
    data: data
  })
}

export function invalid(data) {
  return request({
    url: '/manor/myInvoice/invalid',
    method: 'post',
    data: data
  })
}

export function find(data) {
  return request({
    url: '/manor/myInvoice/findByMany',
    method: 'post',
    data: data
  })
}

