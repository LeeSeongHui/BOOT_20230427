package com.example.config;

import javax.servlet.FilterRegistration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.filter.JwtFilter2;
import com.example.filter.UrlFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FilterConfig {

    @Bean// 서버 구동 시 자동 호출
    //JwtFilter2필터가 적용되는 url을 설정
    public FilterRegistrationBean<JwtFilter2> filterRegistrationBean(JwtFilter2 jwtFilter){
        log.info("filter=>{}", "filterConfig");

        FilterRegistrationBean<JwtFilter2> filterReg=new FilterRegistrationBean<>();
        filterReg.setFilter(jwtFilter);

        filterReg.addUrlPatterns("/api/student2/update.json");
        filterReg.addUrlPatterns("/api/student2/delete.json");

        //filterReg.addUrlPatterns("/api/student2/*"); //student2/전체 시 사용
        return filterReg;
    }

    @Bean// 서버 구동 시 자동 호출
    public FilterRegistrationBean<UrlFilter> filterRegistrationBean1(UrlFilter urlFilter){
        log.info("filter=>{}", "filterConfig");
        FilterRegistrationBean<UrlFilter> filterReg=new FilterRegistrationBean<>();
        filterReg.setFilter(urlFilter);

        filterReg.addUrlPatterns("/api/student2/*"); //student2/전체 시 사용
        return filterReg;
    }
}
