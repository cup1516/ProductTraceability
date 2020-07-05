
export default [
  {
    path:'/AppIndex',
    component:(resolve) => require(['@/views/portal/AppIndex'],resolve),
    hidden:true
  },
  {
    path:'/newsShow',
    component:(resolve) => require(['@/views/portal/news/newsShow'],resolve),
    hidden:true
  },
  {
    path:'/newsDetails',
    component:(resolve) => require(['@/views/portal/news/newsDetails'],resolve),
    hidden:true
  },

  {
    path:'/noticeShow',
    component:(resolve) => require(['@/views/portal/notice/noticeShow'],resolve),
    hidden:true
  },
  {
    path:'/download',
    component:(resolve) => require(['@/views/portal/downloadFile/downloadProfile'],resolve),
    hidden:true
  },
  {
    path:'/announcementShow',
    component:(resolve) => require(['@/views/portal/announcement/announcementShow'],resolve),
    hidden:true
  },
  {
    path:'/announcementDetail',
    component:(resolve) => require(['@/views/portal/announcement/announcementDetail'],resolve),
    hidden:true
  },
  {
    path:'/noticeDetails',
    component:(resolve) => require(['@/views/portal/notice/noticeDetail'],resolve),
    hidden:true
  },
  {
    path:'/commentShow',
    component:(resolve) => require(['@/views/portal/vue_comment/commentShow'],resolve),
    hidden:true
  },
  {
    path:'/bbsList',
    component:(resolve) => require(['@/views/portal/bbs/bbsList'],resolve),
    hidden:true
  },


]

