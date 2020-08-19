const outStore = {
  state: {
    outStoreFunctionDialog: {
      title: '新增出库物品',
      type: 'add'
    }
  },
  getters: {
    outStoreFunctionDialog: state => state.outStoreFunctionDialog
  },
  mutations: {
    SET_OUT_STORE_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.outStoreFunctionDialog.title = '新增出库物品'
        state.outStoreFunctionDialog.type = 'add'
      } else {
        state.outStoreFunctionDialog.title = '编辑出库物品'
        state.outStoreFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default outStore
