package com.common.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.jwt.constant.SecretConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Helon
 * @Description: JWT工具类
 * 参考官网：https://jwt.io/
 * 实现机制参考博客：https://www.cnblogs.com/tyrion1990/p/8134384.html
 * JWT的数据结构为：A.B.C三部分数据，由字符点"."分割成三部分数据
 * A-header头信息
 * B-payload 有效负荷 一般包括：已注册信息（registered claims），公开数据(public claims)，私有数据(private claims)
 * C-signature 签名信息 是将header和payload进行加密生成的
 * @Data: Created in 2018/7/19 14:11
 * @Modified By:
 */
public class JwtUtils {

    /**
     * @param id     - 编号
     * @param subject   - 所有信息
     * @param expirationTimeMillis - 加密时长
     * @Author: Helon
     * @Description: 生成JWT字符串
     * 格式：A.B.C
     * A-header头信息
     * B-payload 有效负荷
     * C-signature 签名信息 是将header和payload进行加密生成的
     * @Data: 2018/7/28 19:26
     * @Modified By:
     */
    public static String generateJWT(String id, String subject, Long expirationTimeMillis) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<String, Object>();
        /*
            Header
            {
              "alg": "HS256",
              "typ": "JWT"
            }
         */
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");


        JSONObject jsonObject = JSONObject.parseObject(subject);
        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        if (null != jsonObject.getString("sessionId") && !"".equals(jsonObject.getString("sessionId"))) {
            claims.put("sessionId", jsonObject.getString("sessionId"));
        }
        if (null != jsonObject.getString("userId") && !"".equals(jsonObject.getString("userId"))) {
            // 也可以进行再次加密
            // AESSecretUtil.encryptToStr(jsonObject.getString("userId"), SecretConstant.DATAKEY);
            claims.put("userId", jsonObject.getString("userId"));
        }
        if (null != jsonObject.getString("username") && !"".equals(jsonObject.getString("username"))) {
            claims.put("username", jsonObject.getString("username"));
        }
        if (null != jsonObject.getString("clientIp") && !"".equals(jsonObject.getString("clientIp"))) {
            claims.put("clientIp", jsonObject.getString("clientIp"));
        }
        if (null != jsonObject.getString("userAgent") && !"".equals(jsonObject.getString("userAgent"))) {
            claims.put("userAgent", jsonObject.getString("userAgent"));
        }
        if (null != jsonObject.get("user")) {
            claims.put("user", jsonObject.get("user"));
        }
        if (null != jsonObject.get("role")) {
            claims.put("role", jsonObject.get("role"));
        }
        if (null != jsonObject.get("permission")) {
            claims.put("permission", jsonObject.get("permission"));
        }

        JwtBuilder builder = Jwts.builder().setHeader(headMap).setId(id)
                //加密后的客户编号
                .setClaims(claims)
                //Signature
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (null != expirationTimeMillis && expirationTimeMillis > 0) {
            long expMillis = nowTimeMillis + expirationTimeMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate).setNotBefore(now);
        } else {
            if (SecretConstant.EXPIRESSECOND >= 0) {
                long expMillis = nowTimeMillis + SecretConstant.EXPIRESSECOND;
                Date expDate = new Date(expMillis);
                builder.setExpiration(expDate).setNotBefore(now);
            }
        }

        return builder.compact();
    }

    /**
     * @param jsonWebToken - JWT
     * @Author: Helon
     * @Description: 解析JWT
     * 返回Claims对象
     * @Data: 2018/7/28 19:25
     * @Modified By:
     */
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        // try {
            if (StringUtils.isNotBlank(jsonWebToken)) {
                //解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
            }
//            else {
//                logger.warn("[JWTHelper]-json web token 为空");
//            }
//        } catch (Exception e) {
//            logger.error("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
//        }
        return claims;
    }

    /**
     * @param jsonWebToken - JWT
     * @Author: Helon
     * @Description: 校验JWT是否有效
     * 返回json字符串的demo:
     * {"freshToken":"A.B.C","userName":"Judy","userId":"123", "userAgent":"xxxx"}
     * freshToken-刷新后的jwt
     * userName-客户名称
     * userId-客户编号
     * userAgent-客户端浏览器信息
     * @Data: 2018/7/24 15:28
     * @Modified By:
     */
    public static String validateLogin(String jsonWebToken) {
        JSONObject returnJSON = new JSONObject();
        Claims claims = parseJWT(jsonWebToken);
        if (claims != null) {
            //解密客户编号
            // String decryptUserId = AESSecretUtil.decryptToStr((String)claims.get("userId"), SecretConstant.DATAKEY);
            //加密后的客户编号
            returnJSON.put("userId", claims.get("userId"));
            //客户名称
            returnJSON.put("sessionId", claims.get("sessionId"));
            //客户端浏览器信息
            returnJSON.put("username", claims.get("username"));
            //客户名称
            returnJSON.put("clientIp", claims.get("clientIp"));
            //客户端浏览器信息
            returnJSON.put("userAgent", claims.get("userAgent"));
            //刷新JWT
            returnJSON.put("freshToken", generateJWT(claims.get("sessionId").toString(), returnJSON.toJSONString(), -1L));
        }
//        else {
//            logger.warn("[JWTHelper]-JWT解析出claims为空");
//        }
        return returnJSON.toJSONString();
    }
}
