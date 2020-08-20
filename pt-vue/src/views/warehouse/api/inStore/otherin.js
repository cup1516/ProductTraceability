import request from '@/utils/request'

/* ------------------------------------查询未提交出库工单的接口------------------------------------ */
export function describeNotCommit (data) {
  const url = '/warehouse/otherin/describe_not_commit?createId=' + data.userId+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

/* ------------------------------------初始化出库工单的接口------------------------------------ */
export function initOrderNo (data) {
  const url = '/warehouse//otherin/init_order_no'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function getAllUser (data) {
  const url = '/warehouse/user/find_all_user'
  return request.get(url).then(res => Promise.resolve(res.data))
}

export function describeMineFuzzy (data) {
  const url = '/warehouse/otherin/describe_mine_fuzzy?userId=' + data.userId + '&otherinNo=' + data.otherinNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}

export function updateOtherinByPrimaryKey (data) {
  const url = '/warehouse/otherin/update_otherin_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批入库单数据的接口（入库）------------------------------------ */
export function describeCommissionFuzzy (data) {
  const url = '/warehouse/otherin/describe_commission_fuzzy?userId=' + data.userId + '&otherinNo=' + data.otherinNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户待审批出库单数据的接口（入库）------------------------------------ */
export function approveOtherin (data) {
  const url = '/warehouse/otherin/approve_otherin'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------条件分页模糊查询当前用户已经审批入库单数据的接口（查询审批记录）------------------------------------ */
export function describeAlreadDoneFuzzy (data) {
  const url = '/warehouse/otherin/describe_already_done_fuzzy?userId=' + data.userId + '&otheroutNo=' + data.otheroutNo + '&pageSize=' + data.pageSize + '&currentPage=' + data.currentPage+'&companyId='+data.companyId
  return request.get(url).then(res => Promise.resolve(res.data))
}
