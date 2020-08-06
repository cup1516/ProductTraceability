const portal = {
  state: {
    url:'',
    company_id:'',
  },
  mutations: {
    SET_URL: (state, url) => {
      state.url = url
    },
    SET_COMPANY_ID: (state, company_id) => {
      state.company_id = company_id
    },
  }
}
export default portal
