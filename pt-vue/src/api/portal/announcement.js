import request from '@/utils/request'

//查询公告列表
export function listAnnouncement(currentPage,company_id) {
  return request({
    url: '/portal/Announcement/findAll/'+(currentPage-1)+'/5/'+company_id,
    method: 'get',
  })
}
//通过id查询公告
export function findAnnouncement(id,company_id) {
  return request({
    url: '/portal/Announcement/findAllById/'+id+'/'+company_id,
    method: 'get',
  })
}
export function findById(id,company_id) {
  return request({
    url: '/portal/Announcement/findById/' +id+'/'+company_id,
    method: 'get',
  })
}
//新增或修改公告
export function saveAnnouncement(data) {
  return request({
    url: '/portal/Announcement/addOrUpdate',
    method: 'post',
    data:data
  })
}
//删除公告
export function deleteAnnouncement(id,company_id) {
  return request({
    url: '/portal/Announcement/delete/'+ id+'/'+company_id,
    method: 'delete',
  })
}
