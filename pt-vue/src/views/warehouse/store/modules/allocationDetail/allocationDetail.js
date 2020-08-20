const allocationDetail = {
  state: {
    allocationDetailFunctionDialog: {
      title: '新增调拨物品',
      type: 'add'
    }
  },
  getters: {
    allocationDetailFunctionDialog: state => state.allocationDetailFunctionDialog
  },
  mutations: {
    SET_ALLOCATION_DETAIL_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.allocationDetailFunctionDialog.title = '新增调拨物品'
        state.allocationDetailFunctionDialog.type = 'add'
      } else {
        state.allocationDetailFunctionDialog.title = '编辑调拨物品'
        state.allocationDetailFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}
export default allocationDetail
