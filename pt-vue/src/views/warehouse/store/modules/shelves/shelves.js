const shelves = {
  state: {
    shelvesFunctionDialog: {
      title: '新增仓库货架',
      type: 'add'
    }
  },
  getters: {
    shelvesFunctionDialog: state => state.shelvesFunctionDialog
  },
  mutations: {
    SET_SHELVES_FUNCTION_DIALOG_TPYE: (state, type) => {
      if (type === 'add') {
        state.shelvesFunctionDialog.title = '新增仓库货架'
        state.shelvesFunctionDialog.type = 'add'
      } else {
        state.shelvesFunctionDialog.title = '编辑仓库货架'
        state.shelvesFunctionDialog.type = 'edit'
      }
    }
  },
  actions: {
  }
}

export default shelves
