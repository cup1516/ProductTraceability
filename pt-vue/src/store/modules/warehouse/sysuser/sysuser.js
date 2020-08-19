const sysuser = {
  state: {
    functionSysUserDialog: {
      title: '编辑用户信息',
      type: 'edit'
    }
  },
  getters: {
    functionSysUserDialog: state => state.functionSysUserDialog
  },
  mutations: {
    SET_FUNCTION_SYSUSER_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.functionSysUserDialog.title = '新增用户信息'
        state.functionSysUserDialog.type = 'add'
      } else {
        state.functionSysUserDialog.title = '编辑用户信息'
        state.functionSysUserDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default sysuser
