const sysmenu = {
  state: {
    functionSysMenuDialog: {
      title: '编辑菜单信息',
      type: 'edit'
    }
  },
  getters: {
    functionSysMenuDialog: state => state.functionSysMenuDialog
  },
  mutations: {
    SET_FUNCTION_SYSMENU_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.functionSysMenuDialog.title = '新增菜单信息'
        state.functionSysMenuDialog.type = 'add'
      } else {
        state.functionSysMenuDialog.title = '编辑菜单信息'
        state.functionSysMenuDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default sysmenu
