import request from '@/utils/request'


export function getCompanyInfo() {
  return request({
    url: '/manor/order/myCompanyInfo',
    method: 'get',
  })
}

export function getCompanyList() {
  return request({
    url: '/manor/order/companyList',
    method: 'get',
  })
}

export function getCompanyName() {
  return request({
    url: '/manor/order/myCompanyName',
    method: 'get',
  })
}

export function add(data) {
  return request({
    url: '/manor/order/add',
    data:data,
    method: 'post',
  })
}

export function ListCompany(query) {
  return request({
    url: '/company_id/company_id/findCompanyIdByNameLike/'+query,
    method: 'get',
  })
}

export function getBatch() {
  return request({
    url: '/manor/order/getBatch',
    method: 'get',
  })
}

export function getCrops() {
  return request({
    url: '/manor/order/getCrops',
    method: 'get',
  })
}

export function getFarmlandRegionId() {
  return request({
    url: '/manor/order/getFarmlandRegionId',
    method: 'get',
  })
}

export function getRealName() {
  return request({
    url: '/user/user/info',
    method: 'get',
  })
}


export function getCheckUser() {
  return request({
    url: '/manor/order/getCheckInvoiceUserList',
    method: 'get',
  })
}



