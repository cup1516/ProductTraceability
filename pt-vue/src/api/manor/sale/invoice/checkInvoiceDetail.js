import request from '@/utils/request'


export function getParams(id) {
  return request({
    url: '/manor/invoice/get',
    method: 'get',
    params: id
  })
}

export function getPrevious(id) {
  return request({
    url: '/manor/invoice/get',
    method: 'get',
    params: id
  })
}


