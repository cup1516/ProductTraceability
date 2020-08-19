import request from '@/utils/request'

/* ------------------------------------查询未提交出库工单的接口------------------------------------ */
export function describeNotCommit (data) {
  const url = '/warehouse/otherout/describe_not_commit?createId=' + data.userId+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------初始化出库工单的接口------------------------------------ */
export function initOrderNo (data) {
  const url = '/warehouse/otherout/init_order_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据仓库id查询货架商品信息的接口------------------------------------ */
export function describeShelvesAndStockByWarehouseId (data) {
  const url = '/warehouse/otherout/describe_shelves_and_stock_by_warehouse_id?warehouseId=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据出库单id更新出库单信息的接口（出库）------------------------------------ */
export function updateOtheroutByPrimaryKey (data) {
  const url = '/warehouse/otherout/update_otherout_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批出库单数据的接口（出库）------------------------------------ */
export function describeCommissionFuzzy (data) {
  const url = '/warehouse/otherout/describe_commission_fuzzy?userId=' + data.userId + '&otheroutNo=' + data.otheroutNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------审批出库单(通过和打回)的接口（出库）------------------------------------ */
export function approveOtherout (data) {
  const url = '/warehouse/otherout/approve_otherout'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批出库单数据的接口（出库）------------------------------------ */
export function describeMineFuzzy (data) {
  const url = '/warehouse/otherout/describe_mine_fuzzy?userId=' + data.userId + '&otheroutNo=' + data.otheroutNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户已经审批出库单数据的接口（查询审批记录）------------------------------------ */
export function describeAlreadDoneFuzzy (data) {
  const url = '/warehouse/otherout/describe_already_done_fuzzy?userId=' + data.userId + '&otheroutNo=' + data.otheroutNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

export function findAllCustomer () {
  const url = '/warehouse/customer/find_all_customer'
  return request.get(url).then(res => Promise.resolve(res.data))
}
