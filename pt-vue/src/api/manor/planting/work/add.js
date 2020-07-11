import request from '@/utils/request'


export function getCheckUser() {
  return request({
    url: '/manor/planting/work/getCheckUsers',
    method: 'get',
  })
}

export function create(data) {
  return request({
    url: '/manor/planting/work/add',
    method: 'post',
    data:data
  })
}



export function getUserName() {
  return request({
    url: 'user/user/info',
    method: 'get',
  })
}

export function getMaterial() {
  return request({
    url: 'manor/planting/work/getMaterial',
    method: 'get',
  })
}

export function getBatch() {
  return request({
    url: 'manor/planting/work/getBatch',
    method: 'get',
  })
}




export function getFarmlandRegionId() {
  return request({
    url: 'manor/planting/work/getFarmlandRegionId',
    method: 'get',
  })
}


export function getCrops() {
  return request({
    url: 'manor/planting/work/getCrops',
    method: 'get',
  })
}

export function getWorkTypes() {
  return request({
    url: 'manor/planting/work/getWorkTypes',
    method: 'get',
  })
}







