const inStore = {
  state: {
    inStoreFunctionDialog: {
      title: '新增入库物品',
      type: 'add'
    }
  },
  getters: {
    inStoreFunctionDialog: state => state.inStoreFunctionDialog
  },
  mutations: {
    SET_IN_STORE_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.inStoreFunctionDialog.title = '新增入库物品'
        state.inStoreFunctionDialog.type = 'add'
      } else {
        state.inStoreFunctionDialog.title = '编辑入库物品'
        state.inStoreFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default inStore
