import request from '@/utils/request'

// 查询节点列表
export function list(query) {
  return request({
    url: '/manor/saleAmount/list',
    method: 'get',
    params: query
  })
}

export function find(data) {
  return request({
    url: '/manor/saleAmount/find',
    method: 'post',
    data: data
  })
}



export function getParams(id) {
  return request({
    url: '/manor/invoice/get',
    method: 'get',
    params: id
  })
}