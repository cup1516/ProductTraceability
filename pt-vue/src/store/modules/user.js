import { login, logout, getInfo } from '@/api/login'
import { treeselect } from '@/api/system/menu'
import { filterMenu } from '@/utils/menu'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { encryption, deepClone } from '@/utils/util'
import { getChatListInfo } from '../../api/chat/chatApi'
import store from '../../store/modules/portal'
import  storage, {USER, ROLE}  from '@/views/warehouse/utils/storage'

const user = {
  state: {
    access_token: getToken(),
    user_name: '',
    user_id:'',
    avatar: '',
    nick_name:'',
    roles: [],
    permissions: [],
    menu:[]
  },

  mutations: {
    SET_NICKNAME: (state, nickName) => {
      state.nick_name = nickName
    },
    SET_TOKEN: (state, token) => {
      state.access_token = token
    },
    SET_USERNAME: (state, name) => {
      state.user_name = name
    },
    SET_ID: (state, id) => {
      state.user_id = id
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_MENU: (state, menu) => {
      state.menu = filterMenu(menu)
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const user = encryption({
        data: userInfo,
        key: '-www.cup.edu.cn-',
        param: ['password']
      })
      const url = store.state.url
      const username = user.username.trim()+"_"+url
      // const username = user.username.trim()
      const password = user.password
      const code = user.code
      const uuid = user.uuid
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid).then(res => {
          console.log(res)
          commit('SET_TOKEN', res.access_token)
          commit('SET_ID',res.user_id)
          commit('SET_USERNAME', res.user_name)
          //仓库代码
          var user = {
            "userId":res.user_id,
            "username":res.user_name
          }
          storage.set(USER, JSON.stringify(user))//仓储
          let params={
            userId:res.user_id
          };
          getChatListInfo(params).then(r=>{
            if(r.code==200){
              commit('chat/setConversationsList',r.data);
              commit('chat/setSelectedRoom',r.data[0]);
            }
            console.log(r)
          })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const data = res.data
          console.log(data)
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
            commit('SET_PERMISSIONS', data.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_AVATAR', data.userDto.avatar)
          commit('SET_NICKNAME', data.userDto.nickName)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取系统菜单
    GetMenu({commit}, obj) {
      return new Promise(resolve => {
        treeselect().then((res) => {
          const menu = res.data
          commit('SET_MENU', deepClone(menu))
          resolve(menu)
        })
      })
    },
    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
