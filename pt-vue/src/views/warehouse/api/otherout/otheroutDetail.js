import request from '@/utils/request'

/* ------------------------------------根据工单号分页查询入库物品信息的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/otherout_detail/describe_fuzzy?pid=' + data.pid + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&commodityName=' + data.commodityName
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据仓储编号新增出库物品信息的接口------------------------------------ */
export function insertByStockId (data) {
  const url = '/warehouse/otherout_detail/insert_by_stock_id'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据仓储编号更新出库物品信息的接口------------------------------------ */
export function updateByStockId (data) {
  const url = '/warehouse/otherout_detail/update_by_stock_id'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据orderDetailId删除出库物品信息的接口------------------------------------ */
export function deleteOtheroutDetailByPrimaryKeys (data) {
  const url = '/warehouse/otherout_detail/delete_otheroutDetail_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------通过货位id查询货架信息的接口------------------------------------ */
export function describeByLocatorId (data) {
  const url = '/warehouse/shelves/describe_by_locator_id?locatorId=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------通过货位id查询货位信息的接口------------------------------------ */
export function describeStockById (data) {
  const url = '/warehouse/stock/describe_by_id?id=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据出库编号清空所有出库子数据的接口------------------------------------ */
export function deleteAllByPid (data) {
  const url = '/warehouse/otherout_detail/delete_all_by_pid'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据出库单no更新出库单信息的接口（绑定仓库信息）------------------------------------ */
export function updateWarehouseIdByOtheroutNo (data) {
  const url = '/warehouse/otherout/update_warehouse_id_by_otherout_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
