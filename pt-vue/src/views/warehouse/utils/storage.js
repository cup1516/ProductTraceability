export const USER = 'user'
export const ROLE = 'role'
export const MENU = 'menu'

export default {
  get (key) {
    return window.sessionStorage.getItem(key)
  },
  set (key, val) {
    window.sessionStorage.setItem(key, val)
  },
  remove (key) {
    window.sessionStorage.removeItem(key)
  },
  clear () {
    window.sessionStorage.clear()
  }
}
