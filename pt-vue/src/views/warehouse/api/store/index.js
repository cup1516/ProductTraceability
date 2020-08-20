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


/*
  以下仓库详情请求
 */
export function getWarehouseById (data) {
  const url = '/warehouse/warehouse/describe_by_id?id='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}

export function getEmptyLocatorsByWarehouseId (data) {
  const url = '/warehouse/locator/describe_full_locators_by_warehouse_id?warehouseId='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}
export function getUseLocatorsByWarehouseId (data) {
  const url = '/warehouse/locator/describe_use_locators_by_warehouse_id?warehouseId='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}
export function getAllLocatorsByWarehouseId (data) {
  const url = '/warehouse/locator/describe_all_locators_by_warehouse_id?warehouseId='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}
export function getStorageByWarehouseId (data) {
  const url = '/warehouse/warehouse/get_storage_by_warehouse_id?id='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}
export function getAllShelvesByWarehouseId (data) {
  const url = '/warehouse/shelves/get_storage_by_warehouse_id?id='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}
export function getAllShelvesNamesByWarehouseId (data) {
  const url = '/warehouse/shelves/describe_all_shelvesName_by_warehouseId?warehouseId='+data
  return request.get(url).then(res => Promise.resolve(res.data))
}


