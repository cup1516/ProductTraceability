const github = 'https://github.com/childwanwan/warehouse-management-system'
const gitee = 'https://gitee.com/Childwanwan/warehouse-management-system'
const appUrl = process.env.VUE_APP_URL // development和production环境是不同的
const shareUrl = 'https://juejin.im/post/5d0b4d28f265da1baf7cf293'
const shareTitle = '用Vue-cli3+element+mockjs 实现后台管理权限系统及顶栏三级菜单显示'
const weibo = {
  'weiboUrl': 'http://service.weibo.com/share/share.php',
  'weiboAppkey': '2003962826',
  'pic': 'https://user-gold-cdn.xitu.io/2019/6/20/16b7425dfa01dbf3?imageView2/1/w/1304/h/734/q/85/format/webp/interlace/1'
}
const qq = {
  'baseUrl': 'http://connect.qq.com/widget/shareqq/index.html',
  'pic': 'https://user-gold-cdn.xitu.io/2019/6/20/16b7425dfa01dbf3?imageView2/1/w/1304/h/734/q/85/format/webp/interlace/1',
  'desc': '最近完成了我的后台管理系统权限功能的实现，同时觉得后台系统所有的菜单都左置，会限制菜单的扩展，因此我改进了三级菜单的显示。',
  'summary': '文章梗概',
  'source': 'qzone'
}
const qqZone = {
  'baseUrl': 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey',
  'pic': 'https://user-gold-cdn.xitu.io/2019/6/20/16b7425dfa01dbf3?imageView2/1/w/1304/h/734/q/85/format/webp/interlace/1',
  'desc': '最近完成了我的后台管理系统权限功能的实现，同时觉得后台系统所有的菜单都左置，会限制菜单的扩展，因此我改进了三级菜单的显示。',
  'summary': '文章梗概',
  'site': 'qzone'
}
const douban = {
  'baseUrl': 'https://www.douban.com/share/service',
  'pic': 'https://user-gold-cdn.xitu.io/2019/6/20/16b7425dfa01dbf3?imageView2/1/w/1304/h/734/q/85/format/webp/interlace/1'
}
const allNation = [
  '汉族', '蒙古族', '回族', '藏族', '维吾尔族', '苗族', '彝族', '壮族', '布依族', '朝鲜族', '满族', '侗族', '瑶族', '白族', '土家族', '哈尼族', '哈萨克族', '傣族', '黎族', '僳僳族', '佤族', '畲族', '高山族', '拉祜族', '水族', '东乡族', '纳西族', '景颇族', '柯尔道克孜族', '土族', '达斡尔族', '仫佬族', '羌族', '布朗族', '撒拉族', '毛南族', '仡佬族', '锡伯族', '阿昌族', '普米族', '塔吉克回族', '怒族', '乌孜别克族', '俄罗斯族', '鄂温克族答', '德昂族', '保安族', '裕固族', '京族', '塔塔尔族', '独龙族', '鄂伦春族', '赫哲族', '门巴族', '珞巴族', '基诺族'
]

const userStyle = [
  '超级管理员', '管理员', '普通员工'
]
export {
  appUrl,
  shareUrl,
  shareTitle,
  weibo,
  qq,
  qqZone,
  douban,
  github,
  gitee,
  allNation,
  userStyle
}
