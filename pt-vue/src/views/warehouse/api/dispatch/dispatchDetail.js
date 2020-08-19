import request from '@/utils/request'

/* ------------------------------------根据仓储编号新增报损物品信息的接口------------------------------------ */
export function insertByStockId (data) {
  const url = '/warehouse/dispatch_detail/insert_by_stock_id'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据仓储编号更新报损物品信息的接口------------------------------------ */
export function updateByStockId (data) {
  const url = '/warehouse/dispatch_detail/update_by_stock_id'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据工单号分页查询报损物品信息的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/dispatch_detail/describe_fuzzy?pid=' + data.pid + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&commodityName=' + data.commodityName
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据orderDetailId删除报损物品信息的接口------------------------------------ */
export function deleteDispatchDetailByPrimaryKeys (data) {
  const url = '/warehouse/dispatch_detail/delete_dispatch_detail_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据报损单编号清空所有报损子数据的接口------------------------------------ */
export function deleteAllByPid (data) {
  const url = '/warehouse/dispatch_detail/delete_all_by_pid'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
