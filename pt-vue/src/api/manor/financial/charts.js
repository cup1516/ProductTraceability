import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/yun/charts/moneyList',
    method: 'get',
    params: query
  })
}

export function getEnterprise() {
  return request({
    url: '/manor/invoice/getEnterprise',
    method: 'get',
  })
}

export function getCrops() {
  return request({
    url: '/manor/invoice/getCrops',
    method: 'get',
  })
}

export function getFindList(data) {
  return request({
    url: '/manor/yun/charts/findList',
    method: 'post',
    data: data
  })
}



