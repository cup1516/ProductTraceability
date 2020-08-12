import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/financial/charts/moneyList',
    method: 'get',
    params: query
  })
}

export function getFindList(data) {
  return request({
    url: '/manor/financial/charts/findList',
    method: 'post',
    data: data
  })
}



