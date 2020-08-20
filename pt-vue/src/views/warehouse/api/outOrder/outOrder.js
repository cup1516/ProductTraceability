import request from '@/utils/request'

export function buildOutOrder (data) {
  const url = '/warehouse/outOrder/insert_outOrder'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function describeFuzzy (data) {
  const url = '/warehouse/outOrder/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&otheroutNo=' + data.otheroutNo
  return request.get(url, data).then(res => Promise.resolve(res.data))
}


export function ListCompany(query) {
  return request({
    url: '/company_id/company_id/findCompanyIdByNameLike/'+query,
    method: 'get',
  })
}


export function getCompanyOrderList1 (data) {
  const url = '/warehouse/outOrder/describe_fuzzy_company?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&otheroutNo=' + data.otheroutNo
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
