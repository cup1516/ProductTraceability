const assignroles = {
  state: {
    functionAssignRolesDialog: {
      title: '分配角色',
      type: 'edit'
    }
  },
  getters: {
    functionAssignRolesDialog: state => state.functionAssignRolesDialog
  },
  mutations: {
    SET_FUNCTION_ASSIGNROLES_DIALOG_TPYE: (state, type) => {
      state.functionAssignRolesDialog.title = '分配角色信息'
      state.functionAssignRolesDialog.type = 'edit'
    }
  },
  actions: {
  }
}

export default assignroles
