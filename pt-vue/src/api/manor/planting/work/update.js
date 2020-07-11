import request from '@/utils/request'


export function update(data) {
  return request({
    url: '/manor/planting/work/update',
    method: 'post',
    data: data
  })
}