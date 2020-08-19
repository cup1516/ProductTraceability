const sysrole = {
  state: {
    functionSysRoleDialog: {
      title: '编辑角色信息',
      type: 'edit'
    }
  },
  getters: {
    functionSysRoleDialog: state => state.functionSysRoleDialog
  },
  mutations: {
    SET_FUNCTION_SYSROLE_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.functionSysRoleDialog.title = '新增角色信息'
        state.functionSysRoleDialog.type = 'add'
      } else {
        state.functionSysRoleDialog.title = '编辑角色信息'
        state.functionSysRoleDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default sysrole
