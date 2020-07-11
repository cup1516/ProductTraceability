import request from '@/utils/request'


export function getParams(id) {
  return request({
    url: '/manor/planting/work/get',
    method: 'get',
    params: id
  })
}