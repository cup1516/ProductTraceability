import request from '@/utils/request'
//查询评论列表
export function listComment(currentPage,company_id) {
  return request({
    url: '/portal/comment/findAllDesc/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
export function loadComment(currentPage,company_id) {
  return request({
    url: '/portal/comment/findAll/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
//通过id查询
export function findComment(id,company_id) {
  return request({
    url: '/portal/comment/findAllById/'+id+'/'+company_id,
    method: 'get',
  })
}
export function findById(id,company_id) {
  return request({
    url: '/portal/comment/findById/' +id+'/'+company_id,
    method: 'get',
  })
}
//新增或修改
export function saveComment(data) {
  return request({
    url: '/portal/comment/addOrUpdate',
    method: 'post',
    data:data
  })
}
//删除
export function deleteComment(id,company_id) {
  return request({
    url: '/portal/comment/delete/'+ id+'/'+company_id,
    method: 'delete',
  })
}
