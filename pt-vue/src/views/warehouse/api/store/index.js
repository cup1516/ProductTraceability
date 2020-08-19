import request from '@/utils/request'

/* ------------------------------------模糊查询仓库的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/warehouse/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&name=' + data.name + '&leaderId=' + data.leaderId+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除仓库的接口------------------------------------ */
export function deleteStoreByPrimaryKeys (data) {
  const url = '/warehouse/warehouse/delete_warehouse_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------新增仓库的接口------------------------------------ */
export function insertStore (data) {
  const url = '/warehouse/warehouse/insert_warehouse'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------更新仓库的接口------------------------------------ */
export function updateStoreByPrimaryKey (data) {
  const url = '/warehouse/warehouse/update_warehouse_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据负责人Id查询仓库的接口------------------------------------ */
export function getAllStoreByPrincipalId (data) {
  const url = '/warehouse/warehouse/get_all_store_by_principal_id?principalId=' + data.principalId + '&companyId=' + data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
  export function getLogList (data) {
    const url = '/warehouse/logging/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&userName=' + data.userName +'&companyId='+data.companyId
    return request.get(url).then(res => Promise.resolve(res.data))
}
