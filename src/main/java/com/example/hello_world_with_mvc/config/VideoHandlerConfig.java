// package com.example.hello_world_with_mvc.config;

// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.resource.ResourceResolver;
// import org.springframework.web.servlet.resource.ResourceResolverChain;

// import com.example.hello_world_with_mvc.utils.VideoHandler;

// import jakarta.servlet.http.HttpServletRequest;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.FileSystemResource;
// import org.springframework.core.io.Resource;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;


// @Configuration
// public class VideoHandlerConfig implements WebMvcConfigurer {
//     @Autowired
//     private VideoHandler videoHandler;
    
//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         // registry.addResourceHandler("/video/**")
//         //         .addResourceLocations("") // 路径由 ATTR_FILE 动态决定
//         //         .resourceChain(true)
//         //         .addResolver((request, path) -> new FileSystemResource((String) request.getAttribute(ATTR_FILE)));
//         registry.addResourceHandler("/videos/**")
//             .addResourceLocations("") // 路径由 ATTR_FILE 动态决定
//             .resourceChain(true)
//             // .addResolver((request, requestPath, locations, chain) -> 
//             //     new FileSystemResource(
//             //         (String) request.getAttribute(VideoHandler.ATTR_FILE)
//             //     )
//             // );
//             .addResolver(new ResourceResolver() {
//                 @Override
//                 public Resource resolveResource(
//                     HttpServletRequest request, 
//                     String requestPath, 
//                     List<? extends Resource> locations, 
//                     ResourceResolverChain chain
//                 ) {
//                     // 核心逻辑：从请求属性中获取动态路径
//                     return new FileSystemResource(
//                         (String) request.getAttribute(VideoHandler.ATTR_FILE)
//                     );
//                 }

//                 @Override
//                 public String resolveUrlPath(
//                     String resourcePath, 
//                     List<? extends Resource> locations, 
//                     ResourceResolverChain chain
//                 ) {
//                     // 此处需返回 URL 路径，若无逻辑直接返回 null
//                     return null;
//                 }
//             });
//     }
// }




