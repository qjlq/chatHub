package com.example.hello_world_with_mvc.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTUtil {
    //private static long time = 1000*10;        // token 有效期为10秒
    private static long time = 1000*60*60*24;   // token 有效期为一天
    private static String signature = "1234567890p[]l";

    // 生成token ，三个参数是我实体类的字段，可根据自身需求来传，一般只需要用户id即可
    public static String createJwtToken(String cid){
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> claims = new HashMap<>();
        claims.put("cid", cid);
        String jwtToken = builder
            // header
            .setHeaderParam("typ","JWT")
            .setHeaderParam("alg","HS256")
            // payload 载荷
            // .claim("username", "admin")
            // .claim("role", "admin")
            // .claim("date", new Date())
            // .setSubject("admin-test")
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + time))
            // .setId(UUID.randomUUID().toString())
            // signature 签名信息
            .signWith(SignatureAlgorithm.HS256, signature)
            // 用.拼接
            .compact();
        return jwtToken;
    }

    // 验证 token 是否还有效，返回具体内容
    public static Claims checkToken(String token){
        if (token == null){
            return null;
        }
        JwtParser parser = Jwts.parser();
        try {
            Jws<Claims> claimsJws = parser.setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            System.out.println(claims.get("username"));
            System.out.println(claims.get("role"));
            System.out.println(claims.getId());
            System.out.println(claims.getSubject()); // 签名
            System.out.println(claims.getExpiration()); // 有效期
            // 如果解析 token 正常，返回 claims
            return claims;
        } catch (Exception e) {
            // 如果解析 token 抛出异常，返回 null
            return null;
        }
    }
}
