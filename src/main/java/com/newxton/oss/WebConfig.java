package com.newxton.oss;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/23
 * @address Shenzhen, China
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);//忽略大小写
        configurer.setPathMatcher(matcher);
    }

}
