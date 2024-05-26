package com.example.hello_world_with_mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.hello_world_with_mvc.utils.TokenInterceptor;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    //do not delete //别删 ######################

    // public void addCorsMappings(CorsRegistry registry) {
    //     // 设置允许跨域的路由
    //     registry.addMapping("/**")
    //             // 设置允许跨域请求的域名
    //             //.allowedOriginPatterns("http://localhost:2235")
    //             .allowedOrigins("http://localhost/")
    //             //设置允许的方法
    //             .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
    //             // 是否允许携带cookie参数
    //             .allowCredentials(true)
    //             // 设置允许的方法
    //             .allowedMethods("*")
    //             // 跨域允许时间
    //             .maxAge(4600)
    //             //表示允许发送身份验证信息，这在需要携带Cookie的情况下非常重要。
    //             //.allowCredentials(true)
    //             ;
    // }

    @Autowired
    private TokenInterceptor interceptor;
  
    /**
     * 重写添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      // 添加自定义拦截器，并拦截对应 url
      registry.addInterceptor(interceptor)
            .addPathPatterns("/user/**")
            .addPathPatterns("/chatcontroller/**");

    }
}

