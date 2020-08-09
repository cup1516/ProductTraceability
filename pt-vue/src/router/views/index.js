import Layout from '@/layout'
import store from '../../store/modules/portal'
store.state.url= window.location.hash.split('/')[1]
console.log(store.state.url)
export default [
  {
    path: '/'+store.state.url,
    hidden: true,
    redirect : '/'+store.state.url+'/AppIndex',
    component: (resolve) => require(['@/views/portal/portalIndex'], resolve),
    children: [
      {
        path: 'AppIndex',
        name:'AppIndex',
        component: (resolve) => require(['@/views/portal/AppIndex'], resolve),
      },
      {
        path:'newsShow',
        name:'newsShow',
        component:(resolve) => require(['@/views/portal/news/newsShow'],resolve),
        // hidden:true
      },
      {
        path:'newsDetails',
        name:'newsDetails',
        component:(resolve) => require(['@/views/portal/news/newsDetails'],resolve),
        hidden:true
      },

      {
        path:'noticeShow',
        name:'noticeShow',
        component:(resolve) => require(['@/views/portal/notice/noticeShow'],resolve),
        hidden:true
      },
      {
        path:'download',
        component:(resolve) => require(['@/views/portal/downloadFile/downloadProfile'],resolve),
        hidden:true
      },
      {
        path:'announcementShow',
        name:'announcementShow',
        component:(resolve) => require(['@/views/portal/announcement/announcementShow'],resolve),
        hidden:true
      },
      {
        path:'announcementDetail',
        name:'announcementDetail',
        component:(resolve) => require(['@/views/portal/announcement/announcementDetail'],resolve),
        hidden:true
      },
      {
        path:'noticeDetails',
        name:'noticeDetails',
        component:(resolve) => require(['@/views/portal/notice/noticeDetail'],resolve),
        hidden:true
      },
      {
        path:'commentShow',
        name:'commentShow',
        component:(resolve) => require(['@/views/portal/vue_comment/commentShow'],resolve),
        hidden:true
      },
      {
        path:'bbsList',
        component:(resolve) => require(['@/views/portal/bbs/bbsList'],resolve),
        hidden:true
      },
    ],
  },
  {
    path: '/'+store.state.url+'/Management',
    component: Layout,
    redirect : '/'+store.state.url+'/Management/index',
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: '首页',
        meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true }
      }
    ]
  },
  {
    path: '/'+store.state.url+'/login',
    name: 'login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },

]

