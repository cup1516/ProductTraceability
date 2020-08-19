
import request from '@/utils/request'

/* ------------------------------------模糊查询我的商品的接口------------------------------------ */
/* eslint-disable */
export function describeFuzzyGoods (data) {
  const url = '/warehouse/good/describe_fuzzy?pageSize='+data.pageSize+'&currentPage='+data.currentPage+'&goodName='+data.goodsName+'&leaderId='+data.leaderId+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除商品的接口------------------------------------ */
/* eslint-disable */
export function deleteGoodsByPrimaryKeys (data) {
  const url = '/warehouse/good/delete_good_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
/* ------------------------------------商品添加接口------------------------------------ */
/* eslint-disable */
export function addGoods (data) {
  const url = '/warehouse/good/insert_good'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------商品修改接口------------------------------------ */
/* eslint-disable */
export function updateGoodsByPrimaryKey (data) {
  const url = '/warehouse/good/update_good_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}


export function describeGoodAndGoodcatgory () {
  const url = '/warehouse/good/describe_good_and_goodcategory'
  return request.get(url).then(res => Promise.resolve(res.data))
}
