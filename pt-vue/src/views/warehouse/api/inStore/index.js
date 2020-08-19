import request from '@/utils/request'

/* ------------------------------------我的入库单对应的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/otherin/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&otherinNo=' + data.otherinNo + '&leaderId=' + data.leaderId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除入库单的接口------------------------------------ */
export function deleteInStoreByPrimaryKeys (data) {
  const url = '/warehouse/otherin/delete_otherin_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------入库单修改接口------------------------------------ */
export function updateInStoreByPrimaryKey (data) {
  const url = '/warehouse/otherin/update_otherin_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function decribeShelvesAndLocationByWarehouseId (data) {
  const url = '/warehouse/shelves/describe_usable_shelves_and_location_by_warehouse_id?warehouseId=' + data.warehouseId + '&goodsNo=' + data.goodsNo+'&batch='+data.batch
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function findAllSupplier () {
  const url = '/warehouse/supplier/find_all_supplier'
  return request.get(url).then(res => Promise.resolve(res.data))
}

export function queryEditGoodMsg (data) {
  const url = '/warehouse/good/describe_by_good_no?goodNo=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function queryEditshelvesMsg (data) {
  const url = '/warehouse/locator/describe_by_locator_code?locatorCode=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function querySupplierMsg (data) {
  const url = '/warehouse/otherin/describe_by_otherin_no?otherinNo=' + data
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
