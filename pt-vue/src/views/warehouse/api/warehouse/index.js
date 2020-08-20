import request from '@/utils/request'

/* ------------------------------------根据根据负责人id查询仓库信息的接口------------------------------------ */
export function getAllStoreByPrincipalId (data) {
  const url = '/warehouse/warehouse/get_all_store_by_principal_id?principalId=' + data.principalId+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

