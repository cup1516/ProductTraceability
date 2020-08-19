import request from '@/utils/request'

/* ------------------------------------查询未提交报损工单的接口------------------------------------ */
export function describeNotCommit (data) {
  const url = '/warehouse/dispatch/describe_not_commit?createId=' + data.userId+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------初始化报损工单的接口------------------------------------ */
export function initOrderNo (data) {
  const url = '/warehouse/dispatch/init_order_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据报损单no更新报损单信息的接口（绑定仓库信息）------------------------------------ */
export function updateWarehouseIdByDispatchNo (data) {
  const url = '/warehouse/dispatch/update_warehouse_id_by_dispatch_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据报损单id更新报损单信息的接口（报损）------------------------------------ */
export function updateDispatchByPrimaryKey (data) {
  const url = '/warehouse/dispatch/update_dispatch_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批出库单数据的接口（出库）------------------------------------ */
export function describeMineFuzzy (data) {
  const url = '/warehouse/dispatch/describe_mine_fuzzy?userId=' + data.userId + '&dispatchNo=' + data.dispatchNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批报损单数据的接口（报损）------------------------------------ */
export function describeCommissionFuzzy (data) {
  const url = '/warehouse/dispatch/describe_commission_fuzzy?userId=' + data.userId + '&dispatchNo=' + data.dispatchNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------审批报损单(通过和打回)的接口（报损）------------------------------------ */
export function approveDispatch (data) {
  const url = '/warehouse/dispatch/approve_dispatch'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户已经审批报损单数据的接口（查询审批记录）------------------------------------ */
export function describeAlreadDoneFuzzy (data) {
  const url = '/warehouse/dispatch/describe_already_done_fuzzy?userId=' + data.userId + '&dispatchNo=' + data.dispatchNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}
