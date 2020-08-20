import Vue from 'vue'
import Vuex from 'vuex'

import menu from './modules/menu'
import user from './modules/user'
import employee from './modules/employee/employee'
import sysuser from './modules/sysuser/sysuser'
import sysrole from './modules/sysrole/sysrole'
import sysmenu from './modules/sysmenu/sysmenu'
import assignroles from './modules/assignroles/assignroles'
import outStore from './modules/outStore/outStore'
import inStore from './modules/inStore/inStore'
import dispatch from './modules/dispatch/dispatch'
import allocationDetail from './modules/allocationDetail/allocationDetail.js'
import shelves from './modules/shelves/shelves'
import locator from './modules/locator/locator'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
    menu,
    employee,
    sysuser,
    outStore,
    assignroles,
    inStore,
    sysrole,
    sysmenu,
    dispatch,
    allocationDetail,
    shelves,
    locator
  }
})
