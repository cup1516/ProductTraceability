import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/saleAmount/chart',
    method: 'get',
    params: query
  })
}

export function getFindList(data) {
  return request({
    url: '/manor/saleAmount/findList',
    method: 'post',
    data: data
  })
}

export function getCrops() {
  return request({
    url: '/manor/invoice/getCrops',
    method: 'get',
  })
}
export function getEnterprise() {
  return request({
    url: '/manor/invoice/getEnterprise',
    method: 'get',
  })
}
