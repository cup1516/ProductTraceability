import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/manor/planting/farmlandRegion/list',
    method: 'get',
    params: query
  })
}

export function create(data) {
  return request({
    url: '/manor/planting/farmlandRegion/add',
    method: 'post',
    data: data
  })
}

export function isDelete(data) {
  return request({
    url: '/manor/planting/farmlandRegion/delete',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/manor/planting/farmlandRegion/update',
    method: 'post',
    data: data
  })
}

export function find(data) {
  return request({
    url: '/manor/planting/farmlandRegion/findByMany',
    method: 'post',
    data: data
  })
}




export function getFarmlandRegionType() {
  return request({
    url: '/manor/planting/farmlandRegion/getFarmlandRegionType',
    method: 'get',
  })
}

export function getAllFarmlandLocation() {
  return request({
    url: '/manor/planting/farmlandRegion/farmlandLocationName',
    method: 'get',
  })
}

export function getParams(id) {
  return request({
    url: '/manor/planting/farmlandRegion/get',
    method: 'get',
    params: id
  })
}





