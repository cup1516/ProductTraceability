import request from '@/utils/request'
//查询所有通过审核的宣传栏列表
export function listNotice(currentPage,company_id) {
  return request({
    url: '/portal/notice/findAllDesc/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
//获取所有没有通过审核的宣传
export function listNoticeAll(currentPage,company_id) {
  return request({
    url: '/portal/notice/findAll/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
//查找当前用户的所有宣传内容
export function loadNotice(currentPage,user_name,company_id) {
  return request({
    url: '/portal/notice/findAllNotice/'+(currentPage-1)+'/5/'+user_name+'/'+company_id,
    method: 'get',
  })
}
//通过审核
export function backCheck2(data) {
  return request({
    url: '/portal/notice/backCheck2',
    method: 'post',
    data:data
  })
}
//未通过审核
export function backCheck3(data) {
  return request({
    url: '/portal/notice/backCheck3',
    method: 'post',
    data:data
  })
}
//通过id查询
export function findNotice(id,company_id) {
  return request({
    url: '/portal/notice/findAll/'+id+'/'+company_id,
    method: 'get',
  })
}
//撤回审核操作
export function backCheck(data) {
  return request({
    url: '/portal/notice/backCheck',
    method: 'post',
    data:data
  })
}
//送审
export function tocheck(data) {
  return request({
    url: '/portal/notice/tocheck',
    method: 'post',
    data:data
  })
}
export function findById(id,company_id) {
  return request({
    url: '/portal/notice/findById/' +id+'/'+company_id,
    method: 'get',
  })
}
//新增或修改
export function saveNotice(data) {
  return request({
    url: '/portal/notice/addOrUpdate',
    method: 'post',
    data:data
  })
}
//删除新闻
export function deleteNotice(id,company_id) {
  return request({
    url: '/portal/notice/delete/'+ id+'/'+company_id,
    method: 'delete',
  })
}
