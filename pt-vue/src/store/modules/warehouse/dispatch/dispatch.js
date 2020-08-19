const dispatch = {
  state: {
    dispatchFunctionDialog: {
      title: '新增报损物品',
      type: 'add'
    }
  },
  getters: {
    dispatchFunctionDialog: state => state.dispatchFunctionDialog
  },
  mutations: {
    SET_DISPATCH_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.dispatchFunctionDialog.title = '新增报损物品'
        state.dispatchFunctionDialog.type = 'add'
      } else {
        state.dispatchFunctionDialog.title = '编辑报损物品'
        state.dispatchFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default dispatch
