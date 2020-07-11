import request from '@/utils/request'

// 查询节点列表
export function list(query) {
  return request({
    url: '/manor/production/chart',
    method: 'get',
    params: query
  })
}

export function getFindList(data) {
  return request({
    url: '/manor/production/findChart',
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

