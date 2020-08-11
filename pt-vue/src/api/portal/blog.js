import request from '@/utils/request'
//查询列表
export function listBlog(currentPage,company_id) {
  return request({
    url: '/portal/blog/findAllDesc/' + (currentPage - 1) + '/5/' + company_id,
    method: 'get',
  })
}
export function loadBlogcomment(company_id) {
  return request({
    url: '/portal/Blogcomment/getAll/'+company_id,
    method: 'get',
  })
}
//通过id查询
export function showBlog(id,company_id) {
  return request({
    url: '/portal/Blogcomment/get/by-blog/'+id+'/'+company_id,
    method: 'get',
  })
}
export function getBlog(id,company_id) {
  return request({
    url: '/portal/blog/get/by-BlogId/'+id+'/'+company_id,
    method: 'get',
  })
}
export function findById(id,company_id) {
  return request({
    url: '/portal/News/findById/' +id+'/'+company_id,
    method: 'get',
  })
}
//新增或修改帖子
export function saveBlog(data) {
  return request({
    url: '/portal/blog/addOrUpdate',
    method: 'post',
    data:data
  })
}
//新增或修改帖子评论
export function saveBlogcomment(data) {
  return request({
    url: '/portal/Blogcomment/addOrUpdate',
    method: 'post',
    data:data
  })
}
//删除
export function deleteBlog(id,company_id) {
  return request({
    url: '/portal/blog/delete/'+ id+'/'+company_id,
    method: 'delete',
  })
}
