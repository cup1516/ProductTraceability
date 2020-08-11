import request from '@/utils/request'

export function update(data) {
  return request({
    url: '/manor/order/update',
    data:data,
    method: 'post',
  })
}

export function getParams(id) {
  return request({
    url: '/manor/order/get',
    method: 'get',
    params: id
  })
}
