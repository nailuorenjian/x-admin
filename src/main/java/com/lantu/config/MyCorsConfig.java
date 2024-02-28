package com.lantu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfig {
    //全局跨域处理
    // 个别可以用@CrossOrigin解决跨域问题
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:9528"); //允许访问的地址，前端的地址1
        configuration.setAllowCredentials(true);//允许cookie
        configuration.addAllowedMethod("*");//允许方法

        //允许的头信息
        configuration.addAllowedHeader("*");

        //映射路径，拦截一切
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        //返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


}
