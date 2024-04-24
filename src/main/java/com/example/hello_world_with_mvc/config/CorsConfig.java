package com.example.hello_world_with_mvc.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer{
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                //.allowedOriginPatterns("http://localhost:2235")
                .allowedOrigins("http://localhost/")
                //设置允许的方法
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                // 是否允许携带cookie参数
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(4600)
                //表示允许发送身份验证信息，这在需要携带Cookie的情况下非常重要。
                //.allowCredentials(true)
                ;
    }
}
