import axios from 'axios'
import { Notification, MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// request拦截器
axios.interceptors.request.use(
  config => {
    if (store.getters.access_token) {
      config.headers['Authorization'] = 'Bearer ' +store.getters.access_token // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  // error => {
  //   console.log(error)
  //   Promise.reject(error)
  // }
)

// 响应拦截器
axios.interceptors.response.use(res => {
    const data = res.data
    if(data.code === 1){
      return Promise.reject(data.msg)
    }
    return data
  },
  error => {
    if(error.response.status === 401){
      const code = error.response.data.code
      if (code === 401) {
        MessageBox.confirm(
          '登录状态已过期，您可以继续留在该页面，或者重新登录',
          '系统提示',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      } else {
        Message({
          message: error.response.data.msg,
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(error)
      }  
    }
    // Message({
    //   message: error.response.data.msg,
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    // return Promise.reject(error)
  }
)

export default axios
