import request from '@/utils/request'

/* ------------------------------------查询我的调拨的接口------------------------------------ */
export function findAllocationDetailListByPage (data) {
  const url = '/warehouse/allocationDetail/describe_all_by_pid_on_page?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&allocationId=' + data.allocationId+'companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

export function getAllocationGoodsList (data) {
  if (data.pid == null || data.pid === undefined) {
    data.pid = ''
  }
  if (data.commodityName == null || data.commodityName === undefined) {
    data.commodityName = ''
  }
  const url = '/warehouse/allocationDetail/describe_all?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&pid=' + data.pid + '&commodityName=' + data.commodityName
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据调拨编号清空所有调拨子数据的接口------------------------------------ */
export function deleteAllByPid (data) {
  const url = '/warehouse/otherout_detail/delete_all_by_pid'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------根据allocationDetailId删除出库物品信息的接口------------------------------------ */
export function deleteAllocationDetailByPrimaryKeys (data) {
  const url = '/warehouse/allocationDetail/delete_allocationDetail_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function insertAllocationDetail (data) {
  const url = '/warehouse/allocationDetail/insert_allocationDetail'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

export function updateAllocationDetail (data) {
  const url = '/warehouse/allocationDetail/update_allocationDetail_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
