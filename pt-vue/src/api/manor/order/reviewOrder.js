import request from '@/utils/request'


export function list(query) {
  return request({
    url: '/manor/reviewOrder/toReviewList',
    method: 'get',
    params: query
  })
}

export function pass(data) {
  return request({
    url: '/manor/reviewOrder/pass',
    method: 'post',
    data: data
  })
}

export function reviewBack(data) {
  return request({
    url: '/manor/reviewOrder/reviewBack',
    method: 'post',
    data: data
  })
}

export function toCheck(data) {
  return request({
    url: '/manor/reviewOrder/toCheck',
    method: 'post',
    data: data
  })
}


export function backToCheck(data) {
  return request({
    url: '/manor/reviewOrder/backToCheck',
    method: 'post',
    data: data
  })
}

export function find(data) {
  return request({
    url: '/manor/reviewOrder/findByMany',
    method: 'post',
    data: data
  })
}

export function getStaff() {
  return request({
    url: '/manor/reviewOrder/getFinancialUserList',
    method: 'get',
  })
}






