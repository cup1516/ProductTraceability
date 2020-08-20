import request from '@/utils/request'

/* ------------------------------------模糊查询我的货架的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/shelves/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&shelvesName=' + data.shelvesName + '&warehouseId=' + data.warehouseId + '&createUserId=' + data.createUserId+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------新增我的货架的接口------------------------------------ */
export function insertShelves (data) {
  const url = '/warehouse/shelves/insert_shelves'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------修改我的货架的接口------------------------------------ */
export function updateShelvesByPrimaryKey (data) {
  const url = '/warehouse/shelves/update_shelves_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------删除我的货架的接口------------------------------------ */
export function deleteShelvesByPrimaryKeys (data) {
  const url = '/warehouse/shelves/delete_shelves_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------级联查询我的仓库货架的接口------------------------------------ */
export function describeAllMineGroupByWarehouse (data) {
  const url = '/warehouse/shelves/describe_all_mine_group_by_warehouse?userId=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}


export function getShelfDetail (data) {
  const url = '/warehouse/shelves/describe_shelf_detail?id=' + data
  return request.get(url).then(res => Promise.resolve(res.data))
}
