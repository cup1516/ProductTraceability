import request from '@/utils/request'
//查询新闻列表
export function listNews(currentPage,company_id) {
  return request({
    url: '/portal/News/findAllDesc/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
export function loadNews(currentPage,company_id) {
  return request({
    url: '/portal/News/findAll/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
//通过id查询新闻
export function findNew(id,company_id) {
  return request({
    url: '/portal/News/findAllById/'+id+'/'+company_id,
    method: 'get',
  })
}
export function findById(id,company_id) {
  return request({
    url: '/portal/News/findById/' +id+'/'+company_id,
    method: 'get',
  })
}
//新增或修改新闻
export function saveNews(data) {
  return request({
    url: '/portal/News/addOrUpdate',
    method: 'post',
    data:data
  })
}
//删除新闻
export function deleteNews(id,company_id) {
  return request({
    url: '/portal/News/delete/'+ id+'/'+company_id,
    method: 'delete',
  })
}
