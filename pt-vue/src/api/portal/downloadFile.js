import request from '@/utils/request'
//查询列表
export function listFile(currentPage,company_id) {
  return request({
    url: '/portal/download/findAllFile/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
