package com.example.hello_world_with_mvc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT token加解密工具类
 */
@Slf4j
public class TokenUtil {

    private static String secretKey = "jweijo1231ojiSDJOIA23Ssdii";// 密钥签名

    // private static Integer end_day = 7;// 密钥过期天数

    // private static long time = 1000*60*60*24; // token 有效期为一天
    private static long time = 1000 * 60 * 60 * 2; // token 有效期为2h

    /**
     * token加密
     * 
     * @param id 账号
     * @return
     */
    public static String signToken(String cid) {
        HashMap<String, Object> map = new HashMap<>();

        // // 指定令牌的过期时间，这里是【7天】后令牌token失效
        // Calendar instance = Calendar.getInstance();
        // instance.add(Calendar.HOUR, end_day);

        // 生成token
        String token = JWT.create()
                .withHeader(map) // header可以不写，因为默认值就是它
                .withClaim("cid", cid) // payload
                .withClaim("timeStamp", System.currentTimeMillis())
                // .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + time)) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256(secretKey));// 签名

        return token;
    }

    /**
     * 验证token合法性
     * 
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        try {
            DecodedJWT jwt =  JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return jwt;
        } catch (JWTVerificationException e) {
            // 无效的签名/声明
            log.info("token：过期 cid: {} date: {}" );
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解析token.
     * {
     * "userId": "weizhong",
     * "userRole": "ROLE_ADMIN",
     * "timeStamp": "134143214"
     * }
     */
    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim userId = decodedjwt.getClaim("cid");
        Claim userRole = decodedjwt.getClaim("userRole");
        Claim timeStamp = decodedjwt.getClaim("timeStamp");
        map.put("userId", userId.asString());
        map.put("userRole", userRole.asString());
        map.put("timeStamp", timeStamp.asLong().toString());
        return map;
    }

}
