import request from '@/utils/request'

// 查询节点列表
export function listPackage(query) {
    return request({
      url: '/prod/package/page',
      method: 'get',
      params: query
    })
  }
  
  // 查询节点详细
  export function getPackage(packageId) {
    return request({
      url: '/prod/package/' + packageId,
      method: 'get'
    })
  }
  
  // 新增节点
  export function addPackage(data) {
    return request({
      url: '/prod/package',
      method: 'post',
      data: data
    })
  }
  
  // 修改节点
  export function updatePackage(data) {
    return request({
      url: '/prod/package',
      method: 'put',
      data: data
    })
  }
  
  // 删除节点
  export function delPackage(postId) {
    return request({
      url: '/prod/package/' + postId,
      method: 'delete'
    })
  }

  // 查询节点列表
export function getList() {
  return request({
    url: '/prod/package/list',
    method: 'get',
  })
}