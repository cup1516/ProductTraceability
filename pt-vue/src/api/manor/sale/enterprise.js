import request from '@/utils/request'

// 查询节点列表
export function list(query) {
  return request({
    url: '/manor/enterprise/list',
    method: 'get',
    params: query
  })
}