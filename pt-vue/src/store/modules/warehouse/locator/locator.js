const locator = {
  state: {
    locatorFunctionDialog: {
      title: '新增货位',
      type: 'add'
    }
  },
  getters: {
    locatorFunctionDialog: state => state.locatorFunctionDialog
  },
  mutations: {
    SET_LOCATOR_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.locatorFunctionDialog.title = '新增货位'
        state.locatorFunctionDialog.type = 'add'
      } else {
        state.locatorFunctionDialog.title = '编辑货位'
        state.locatorFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default locator
