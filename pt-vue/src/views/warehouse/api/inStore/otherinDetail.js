import request from '@/utils/request'

/* ------------------------------------根据工单号分页查询入库物品信息的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/otherin_detail/describe_fuzzy?pid=' + data.pid + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&commodityName=' + data.commodityName
  return request.get(url).then(res => Promise.resolve(res.data))
}
/* ------------------------------------根据仓储编号更新入库物品信息的接口------------------------------------ */
export function updateByStockId (data) {
  const url = '/warehouse/otherin_detail/update_by_stock_id'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据工单号新增入库物品信息的接口------------------------------------ */
export function insertOtherinDetail (data) {
  const url = '/warehouse/otherin_detail/insert_otherinDetail'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
/* ------------------------------------清空删除未提交子单的接口------------------------------------ */
export function clearNotCommitDetail (data) {
  const url = '/warehouse/otherin_detail/delete_otherinDetail_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function updateWarehouseIdByOtherinNo (data) {
  const url = '/warehouse/otherin/update_warehouse_id_by_otherin_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function updateOtherinDetail (data) {
  const url = '/warehouse/otherin_detail/update_otherinDetail_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
