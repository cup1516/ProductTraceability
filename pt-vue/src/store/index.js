import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import tagsView from './modules/tagsView'
import permission from './modules/permission'
import settings from './modules/settings'
import portal from './modules/portal'
import getters from './getters'
import chat from './modules/chat'
import employee from './modules/warehouse/employee/employee'
import sysuser from './modules/warehouse/sysuser/sysuser'
import sysrole from './modules/warehouse/sysrole/sysrole'
import sysmenu from './modules/warehouse/sysmenu/sysmenu'
import assignroles from './modules/warehouse/assignroles/assignroles'
import outStore from './modules/warehouse/outStore/outStore'
import inStore from './modules/warehouse/inStore/inStore'
import dispatch from './modules/warehouse/dispatch/dispatch'
import allocationDetail from './modules/warehouse/allocationDetail/allocationDetail.js'
import shelves from './modules/warehouse/shelves/shelves'
import locator from './modules/warehouse/locator/locator'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings,
    portal,
    chat,
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
    locator,
  },
  getters
})

export default store
