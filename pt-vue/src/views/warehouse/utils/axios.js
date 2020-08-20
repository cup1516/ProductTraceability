import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import {getToken} from '@/utils/auth'
import router from '@/router/index.js'
import storage from './storage'

// export const baseUrl = 'http://localhost:9999/warehouse-service/'
export const baseUrl = '/warehouse-service/'

// 创建axios实例
let service = axios.create({
  baseURL: baseUrl, // api的base_url
  timeout: 90000 // 请求超时时间
})
// request拦截器
service.interceptors.request.use(config => {
  if (getToken('token') && config.url.indexOf('check_verification_code') === -1 && config.url.indexOf('get_public_secret') === -1 && config.url.indexOf('login') === -1) {
    // 携带权限参数
    config.headers = {'token': getToken('token'), 'Content-Type': 'application/json;charset=UTF-8'}
  }
  return config
}, error => {
  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * code:200,接口正常返回;
     */
    const res = response.data
    if (res.code !== '0000') {
      // console.log(res.code)
      // 根据服务端约定的状态码：5001:非法的token; 5002:其他客户端登录了; 5004:Token 过期了;
      if (res.code === '5001' || res.code === '5002' || res.code === '5004') {
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 重定向到登录页
          storage.clear()
          router.push('/login_by_account')
          location.reload()
        })
      } else {
        Message({
          message: res.msg,
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject(new Error('errorMsg'))
    } else { // res.code === 200,正常返回数据
      return response.data
    }
  },
  error => {
    // 请求为404是登入的sessionId的原因
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
