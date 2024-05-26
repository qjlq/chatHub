package com.example.hello_world_with_mvc.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         // 在这里进行 Token 鉴权逻辑
//         // 获取请求头中的 Token
//         String token = request.getHeader("Authorization");
//         if (token == null || !token.startsWith("Bearer ")) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return false;
//         }

//         // 假设这里对 Token 进行验证，验证通过则放行，否则返回 401 未授权状态码
//         // 这里只是简单示例，实际中可以根据业务需求来验证 Token 的有效性
//         boolean isValidToken = validateToken(token.substring(7)); // 去除 "Bearer " 前缀
//         if (!isValidToken) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return false;
//         }

//         return true;
//     }

//     private boolean validateToken(String token) {
//         // 假设这里对 Token 进行验证，验证通过返回 true，否则返回 false
//         // 可以使用 JWTUtils 工具类来验证 Token 的有效性
//         // 注意：在实际应用中，需要根据业务需求和安全要求来验证 Token
//         return true;
//     }

    // @Autowired
    // StringRedisTemplate redisTemplate;

    // // 处理请求之前执行
    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     // 取出请求头中Authorization的信息，就是token内容，接下来就是各种判断
    //     String requestToken = request.getHeader("Authorization");
    //     if(!StringUtils.isEmpty(requestToken)){
    //         Claims claims = JWTUtil.checkToken(request.getHeader("Authorization"));
    //         if (claims != null) {
    //             String token = redisTemplate.opsForValue().get("operToken"+claims.get("operNo"));
    //             if(Boolean.TRUE.equals(redisTemplate.hasKey("operToken" + claims.get("operNo")))){
    //                 if(requestToken.equals(token)){
    //                     // token正确
    //                     return true;
    //                 }else {
    //                     // token错误，判为并发登录，挤下线
    //                     // 对应的修改响应头的状态，用于前端判断做出相应的策略
    //                     response.setStatus(411);
    //                     return false;
    //                 }
    //             }else {
    //                 // token不存在于redis中，已过期
    //                 response.setStatus(410);
    //                 return false;
    //             }
    //         }
    //         // 解析token中的用户信息claims为null
    //         response.setStatus(409);
    //         return false;
    //     }
    //     // requestToken为空
    //     response.setStatus(409);
    //     return false;
    // }

    // // 处理请求之后执行
    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //     System.out.println("处理请求之后执行");
    // }


     // 注入jwt工具类


  // 重写 前置拦截方法
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 1、从请求头中获取token
    String token = request.getHeader("token");

    // 2、判断 token 是否存在
    if (token == null ||"".equals(token)) {
      //System.out.println("未登录");
      log.info("未登录");
      // 这里可以自定义 抛出 token 异常
      //throw new TokenRuntimeException("未登录");
      return false;
    }

    // 3、解析token
    // Claims claim = JWTUtil.checkToken(token);
    DecodedJWT claim = TokenUtil.verify(token);
    if (null == claim) {
      //System.out.println("token 解析错误");
      log.info("token 解析错误");
      // 这里可以自定义 抛出 token 异常
      //throw  new TokenRuntimeException("token 解析错误");
      return false;

    }
    log.info(claim.getExpiresAt().toString());

    // 5、 从 token 中获取员工信息
    // String subject = claim.getSubject();
    return true;    
  }
}