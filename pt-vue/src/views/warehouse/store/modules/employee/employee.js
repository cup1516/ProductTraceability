const employee = {
  state: {
    functionDialog: {
      title: '新增职工信息',
      type: 'add'
    }
  },
  getters: {
    functionDialog: state => state.functionDialog
  },
  mutations: {
    SET_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.functionDialog.title = '新增职工信息'
        state.functionDialog.type = 'add'
      } else {
        state.functionDialog.title = '编辑职工信息'
        state.functionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default employee
