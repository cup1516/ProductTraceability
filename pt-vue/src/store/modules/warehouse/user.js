// import { getToken } from '@/utils/auth'

const user = {
  state: {
    id: '',
    name: '',
    // token: getToken('Token'),
    roles: [],
    avatar: '',
    currentRoleId: '',
    currentRoleName: ''
  },
  getters: {
    // token: state => state.token,
    roles: state => state.roles,
    name: state => state.name,
    id: state => state.id,
    avatar: state => state.avatar,
    currentRoleId: state => state.currentRoleId,
    currentRoleName: state => state.currentRoleName
  },
  mutations: {
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_CURRENT_ROLE_ID: (state, id) => {
      state.currentRoleId = id
    },
    SET_CURRENT_ROLE_NAME: (state, name) => {
      state.currentRoleName = name
    }
  },
  actions: {
  }
}

export default user
