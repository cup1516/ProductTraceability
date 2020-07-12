import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/planting/work/finalList',
    method: 'get',
    params: query
  })
}

export function find (data) {
  return request({
    url: '/manor/planting/myWork/finalWorkFindByMany',
    method: 'post',
    data: data
  })
}

export function getParams(id) {
  return request({
    url: '/manor/planting/work/get',
    method: 'get',
    params: id
  })
}

