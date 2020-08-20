import request from '@/utils/request'

/* ------------------------------------模糊查询我的货位(带仓库名称)的接口------------------------------------ */
export function describeFuzzyWithWarehouseName (data) {
  const url = '/warehouse/locator/describe_fuzzy_with_warehouse_name?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&name=' + data.name + '&warehouseId=' + data.warehouseId + '&createUserId=' + data.createUserId + '&shelvesId=' + data.shelvesId+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------新增我的货位的接口------------------------------------ */
export function insertLocator (data) {
  const url = '/warehouse/locator/insert_locator'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------修改我的货位的接口------------------------------------ */
export function updateLocatorByPrimaryKey (data) {
  const url = '/warehouse/locator/update_locator_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------删除我的货位的接口------------------------------------ */
export function deleteLocatorByPrimaryKeys (data) {
  const url = '/warehouse/locator/delete_locator_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
export function getLocatorDetail (data) {
  const url = '/warehouse/locator/getLocatorDetail?locatorId='+data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
