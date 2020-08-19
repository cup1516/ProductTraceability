
import request from '@/utils/request'

export function buildInOrder (data) {
  const url = '/warehouse/inOrder/insert_inOrder'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function describeFuzzy (data) {
  const url = '/warehouse/inOrder/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&otherinNo=' + data.otherinNo
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
