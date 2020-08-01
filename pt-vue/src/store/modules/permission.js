import Router from '@/router'
import Layout from '@/layout/index'
import {getRouters} from '@/api/system/menu'
import store from '../../store/modules/portal'

const permission = {
  state: {
    routes: [],
    addRoutes: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = routes
      Router.addRoutes(routes)
    }
  },
  actions: {
    // 生成路由
    GenerateRoutes({ commit }) {
        return new Promise(resolve => {
            // 向后端请求路由数据
            getRouters().then(res => {
              const accessedRoutes = filterAsyncRouter(res.data)
              accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
              commit('SET_ROUTES', accessedRoutes)
              resolve(accessedRoutes)
            })
          })
    }
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
  return asyncRouterMap.filter(route => {
    if (route.component) {
      // Layout组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else {
        route.component = loadView(route.component)
      }
    }
    //一级菜单的path补全
    // if(route.parentId === "-1"){
      route.path = '/'+store.state.url +'/'+ route.path
    // }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) =>  require([`@/views/${view}`], resolve)
}

export default permission
