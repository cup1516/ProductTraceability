import request from '@/utils/request'

// 查询操作日志列表
export function list(query) {
  return request({
    url: '/manor/monitor/syslog/list',
    method: 'get',
    params: query
  })
}

export function test() {
  return request({
    url: '/log/monitor/syslog/test',
    method: 'get',
  })
}

// 删除操作日志
export function delOperlog(operId) {
  return request({
    url: '/log/monitor/operlog/' + operId,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperlog() {
  return request({
    url: '/log/monitor/operlog/clean',
    method: 'delete'
  })
}

// 导出操作日志
export function exportOperlog(query) {
  return request({
    url: '/log/monitor/operlog/export',
    method: 'get',
    params: query
  })
}