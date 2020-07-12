import request from '@/utils/request'


// 登录方法
export function login(username, password, code ) {
  const grant_type = 'password'
  const scope = 'all'
  return request({
    url: '/auth/oauth/token',
    headers: {
      isToken:false,
     'Authorization': 'Basic ZGVhbGVyOnNlY3JldA=='//dealer:secret
      // 'Authorization': 'Basic Y2VudGVyOnNlY3JldA=='//center:secret
      //'Authorization': 'Basic bWFub3I6c2VjcmV0'//dealer:manor
    },
    method: 'post',
    params: { username, password, code, grant_type, scope }
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/user/user/info/',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/auth/token/logout',
    method: 'delete'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method: 'get'
  })
}