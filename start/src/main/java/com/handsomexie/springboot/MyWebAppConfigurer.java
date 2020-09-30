package com.handsomexie.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Value("${url}")
    private String url;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String path = System.getProperty("user.dir")+"/src/main/resources/static/img/";
//        path = path.replace("\\","/");
        registry.addResourceHandler("/pic/**").addResourceLocations("file:"+url);
    }
}