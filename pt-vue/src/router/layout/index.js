
/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */

// 公共路由
export default [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: (resolve) => require(['@/views/redirect'], resolve)
      }
    ]
  },
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: '首页',
        meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true }
      }
    ]
  },
  // {
  //   path: '/:id',
  //   redirect: '/:id/index',
  //   component: (resolve) => require(['@/views/portal/AppIndex'], resolve),
  //   children: [
  //     {
  //       path: '/:id/index',
  //       // component: (resolve) => require(['@/views/portal/AppIndex'], resolve),
  //       name: '首页',
  //       meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true },
  //     }
  //   ]
  // },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  {
    path: '/dict',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'type/data/:dictId(\\d+)',
        component: (resolve) => require(['@/views/system/dict/data'], resolve),
        name: 'Data',
        meta: { title: '字典数据', icon: '' }
      }
    ]
  },
  {
    path: '/job',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'log',
        component: (resolve) => require(['@/views/monitor/job/log'], resolve),
        name: 'JobLog',
        meta: { title: '调度日志' }
      }
    ]
  },
  {
    path: '/gen',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'edit',
        component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
        name: 'GenEdit',
        meta: { title: '修改生成配置' }
      }
    ]
  },

  // {
  //   path: '/system',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: 'config',
  //       component: (resolve) => require(['@/views/system/config'], resolve),
  //       name: 'GenEdit',
  //       meta: { title: '修改生成配置' }
  //     }
  //   ]
  // }
   //manor项目的一些路由配置,勿删
 
   {
    path: '/detail',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'myInvoiceDetail',
        component: (resolve) => require(['@/views/manor/sale/invoice/detail'], resolve),
        name: 'myInvoiceDetail',
        meta: { title: '订单详情', icon: 'user' }
      },
      {
        path: 'checkInvoiceDetail',
        component: (resolve) => require(['@/views/manor/sale/invoice/checkInvoiceDetail'], resolve),
        name: 'checkInvoiceDetail',
        meta: { title: '订单详情', icon: 'user' }
      },
      {
        path: 'myWorkDeatil',
        component: (resolve) => require(['@/views/manor/planting/work/detail'], resolve),
        name: 'myWorkDeatil',
        meta: { title: '工作详情', icon: 'user' }
      },
      {
        path: 'invoiceUpdate',
        component: (resolve) => require(['@/views/manor/sale/invoice/update'], resolve),
        name: 'invoiceUpdate',
        meta: { title: '修改票据', icon: 'user' }
      },
      {
        path: 'addFarmlandLocation',
        component: (resolve) => require(['@/views/manor/planting/farmlandLocation/add'], resolve),
        name: 'addFarmlandLocation',
        meta: { title: '创建田地区域', icon: 'user' }
      },
      {
        path: 'updateFarmlandLocation',
        component: (resolve) => require(['@/views/manor/planting/farmlandLocation/update'], resolve),
        name: 'updateFarmlandLocation',
        meta: { title: '更新田地区域', icon: 'user' }
      },
      {
        path: 'addFarmlandRegion',
        component: (resolve) => require(['@/views/manor/planting/farmlandRegion/addFarmlandRegion'], resolve),
        name: 'addFarmlandRegion',
        meta: { title: '创建田地区块', icon: 'user' }
      },
      {
        path: 'updateFarmlandRegion',
        component: (resolve) => require(['@/views/manor/planting/farmlandRegion/update'], resolve),
        name: 'updateFarmlandRegion',
        meta: { title: '修改田地区块', icon: 'user' }
      },
      {
        path: 'toCheckWorkDetail',
        component: (resolve) => require(['@/views/manor/planting/work/toCheckWorkDetail'], resolve),
        name: 'toCheckWorkDetail',
        meta: { title: '作业单详情', icon: 'user' }
      },
      {
        path: 'workDetailCanUpdate',
        component: (resolve) => require(['@/views/manor/planting/work/detailCanUpdate'], resolve),
        name: 'workDetailCanUpdate',
        meta: { title: '作业单详情', icon: 'user' }
      },
      {
        path: 'updateWork',
        component: (resolve) => require(['@/views/manor/planting/work/update'], resolve),
        name: 'workDetailCanUpdate',
        meta: { title: '修改作业单', icon: 'user' }
      },
      {
        path: 'finalWorkDetail',
        component: (resolve) => require(['@/views/manor/planting/work/finalWorkDetail'], resolve),
        name: 'finalWorkDetail',
        meta: { title: '作业单详情', icon: 'user' }
      },
      {
        path: 'financial',
        component: (resolve) => require(['@/views/manor/financial/invoiceDetail'], resolve),
        name: 'finalWorkDetail',
        meta: { title: '票据详情', icon: 'user' }
      },
      {
        path: 'production/workDetail',
        component: (resolve) => require(['@/views/manor/production/workDetail'], resolve),
        name: 'workDetail',
        meta: { title: '工作单详情', icon: 'user' }
      },
      {
        path: 'production/invoiceDetail',
        component: (resolve) => require(['@/views/manor/production/invoiceDetail'], resolve),
        name: 'workDetail',
        meta: { title: '票据详情', icon: 'user' }
      },
    ]
  },
]

