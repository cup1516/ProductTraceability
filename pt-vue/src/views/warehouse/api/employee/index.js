import request from '@/utils/request'

/* ------------------------------------模糊查询我的职工的接口------------------------------------ */
export function describeFuzzy (data) {
  const url = '/warehouse/employee/describe_fuzzy?pageSize=' + data.pageSize + '&currentPage=' + data.currentPage + '&employeeName=' + data.employeeName + '&leaderId=' + data.leaderId+'&companyId='+data.companyId
  return request.get(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------批量删除职工的接口------------------------------------ */
export function deleteEmployeeByPrimaryKeys (data) {
  const url = '/warehouse/employee/delete_employee_by_primary_keys'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------新增职工的接口------------------------------------ */
export function insertEmployee (data) {
  const url = '/warehouse/employee/insert_employee'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}

/* ------------------------------------更新职工的接口------------------------------------ */
export function updateEmployeeByPrimaryKey (data) {
  const url = '/warehouse/employee/update_employee_by_primary_key'
  return request.post(url, data).then(res => Promise.resolve(res.data))
}
