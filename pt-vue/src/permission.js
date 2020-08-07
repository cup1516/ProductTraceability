import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import store2 from './store/modules/portal'

NProgress.configure({ showSpinner: false })
console.log(store2.state.url)

const whiteList = [ '/auth-redirect', '/bind', '/register',
  '/'+store2.state.url+'/login',
  '/'+store2.state.url+'/AppIndex',
  '/'+store2.state.url+'/newsShow',
  '/'+store2.state.url+'/announcementShow',
  '/'+store2.state.url+'/commentShow',
  '/'+store2.state.url+'/bbsList',
  '/'+store2.state.url+'/noticeShow',
  '/'+store2.state.url+'/download',
  '/'+store2.state.url+'/newsDetails',
  '/'+store2.state.url+'/announcementDetail',
  '/'+store2.state.url+'/noticeDetails']

router.beforeEach((to, from, next) => {
   // store2.state.url = to.path.split('/')[1]
  NProgress.start()
  if (store.getters.access_token) {
    /* has token*/
    if (to.path === '/'+store2.state.url+'/login') {
      // next({ path: '/' })
      next()
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(res => {
          // 拉取user_info
          const roles = res.roles
          store.dispatch('GetMenu').then(accessRoutes => {
            store.dispatch('GenerateRoutes',accessRoutes)
          // 测试 默认静态页面
          // store.dispatch('permission/generateRoutes', { roles }).then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            // console.log(accessRoutes)
            // router.addRoutes(accessRoutes) // 动态添加可访问路由表
            // next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
            next({ path: '/'+store2.state.url+'Management/index' })
          })
        })
          .catch(err => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err)
              next({ path: '/'+store2.state.url })
            })
          })
      } else {
        next()
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        // if (hasPermission(store.getters.roles, to.meta.roles)) {
        //   next()
        // } else {
        //   next({ path: '/401', replace: true, query: { noGoBack: true }})
        // }
        // 可删 ↑
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      // next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      // next('/login')
      next('/'+store2.state.url+'/AppIndex')
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
