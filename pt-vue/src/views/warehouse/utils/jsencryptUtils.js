import JSEncrypt from 'jsencrypt'

// 公钥加密
export function encryptedData (publicKey, data) {
  // 新建JSEncrypt对象
  let encryptor = new JSEncrypt()
  // 设置公钥
  encryptor.setPublicKey(publicKey)
  // 加密数据
  return encryptor.encrypt(data)
}
// 私钥解密
export function decryptedData (privateKey, data) {
  // 新建JSEncrypt对象
  let encryptor = new JSEncrypt()
  // 设置公钥
  encryptor.setPrivateKey(privateKey)
  // 加密数据
  return encryptor.decrypt(data)
}
