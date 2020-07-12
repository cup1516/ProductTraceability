import request from '@/utils/request'

// 查询节点列表
export function list(query) {
  return request({
    url: '/manor/checkInvoice/toCheckList',
    method: 'get',
    params: query
  })
}


export function pass(data) {
  return request({
    url: '/manor/checkInvoice/pass',
    method: 'post',
    data: data
  })
}

export function checkBack(data) {
  return request({
    url: '/manor/checkInvoice/checkBack',
    method: 'post',
    data: data
  })
}



export function find(data) {
  return request({
    url: '/manor/checkInvoice/findByMany',
    method: 'post',
    data: data
  })
}


export function getStaff() {
  return request({
    url: '/manor/checkInvoice/getFinancialUserList',
    method: 'get',
  })
}





