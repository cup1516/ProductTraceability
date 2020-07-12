import request from '@/utils/request'


export function createInvoice(data) {
  return request({
    url: '/manor/myInvoice/add',
    method: 'post',
    data:data
  })
}

export function getCheckUser() {
  return request({
    url: '/manor/invoice/getCheckInvoiceUserList',
    method: 'get',
  })
}

export function getRealName() {
  return request({
    url: '/user/user/info',
    method: 'get',
  })
}

export function getEnterprise() {
  return request({
    url: '/manor/invoice/getEnterprise',
    method: 'get',
  })
}

export function getFarmlandRegionId() {
  return request({
    url: '/manor/invoice/getFarmlandRegionId',
    method: 'get',
  })
}

export function getCrops() {
  return request({
    url: '/manor/invoice/getCrops',
    method: 'get',
  })
}

export function getBatch() {
  return request({
    url: '/manor/invoice/getBatch',
    method: 'get',
  })
}

export function findBuyer(data) {
  return request({
    url: '/manor/enterprise/findByName',
    method: 'post',
    data:data
  })
}

