import request from '@/utils/request'

/* ------------------------------------查询我的职工的接口------------------------------------ */
export function describeFuzzy (data) {
  // Todo
  const url = '/warehouse/allocation/describe_all?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除接口------------------------------------ */
export function deleteAllocationByPrimaryKeys (data) {
  const url = '/warehouse/allocation/delete_allocation_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据调拨单id更新调拨单信息的接口（调拨）------------------------------------ */
export function updateAllocationByPrimaryKey (data) {
  const url = '/warehouse/allocation/update_allocation_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------查询未提交出库工单的接口------------------------------------ */
export function describeNotCommit (data) {
  const url = '/warehouse/allocation/describe_not_commit?createId=' + data.userId+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------初始化出库工单的接口------------------------------------ */
export function initOrderNo (data) {
  const url = '/warehouse/allocation/init_order_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function updateAllocationByAllocationNo (data) {
  const url = '/warehouse/allocation/update_allocation_by_allocation_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function selectStock (data) {
  const url = '/warehouse/stock/describe_by_id?id=' + data.stockId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------审批调拨单(通过和打回)的接口（出库）------------------------------------ */
export function approveAllocation (data) {
  const url = '/warehouse/allocation/approve_allocation'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function describeCommissionFuzzy (data) {
  const url = '/warehouse/allocation/describe_commission_fuzzy?userId=' + data.userId + '&allocationNo=' + data.allocationNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}
