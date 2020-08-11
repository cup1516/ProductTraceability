import request from '@/utils/request'


export function getParams(id) {
  return request({
    url: '/manor/order/get',
    method: 'get',
    params: id
  })
}