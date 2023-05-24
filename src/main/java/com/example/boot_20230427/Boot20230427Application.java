package com.example.boot_20230427;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


// 새로운 패키지를 생성하기 역할을 부여하면 반드시 실행파일에 등록해야 함.
@EnableAspectJAutoProxy // AOP사용
@EnableScheduling // 스케쥴링 사용
@SpringBootApplication
@PropertySource(value = {"classpath:global.properties"}) // 직접만든 환경설정파일 위치 // classpath ==> resources와 동일함.
@MapperScan(basePackages = {"com.example.mapper"}) // 매퍼 위치
@ComponentScan(basePackages = {"com.example.controller", // 컨트롤러 위치
								"com.example.controller.jpa", 
								"com.example.controller.mybatis",
								"com.example.service",  // 서비스 위치
								"com.example.config",  // 시큐리티환경설정 위치
								"com.example.restcontroller", // 레스트 컨트롤러 위치
								"com.example.filter",
								"com.example.scheduler",
								"com.example.interceptor",
								"com.example.aop"}) 

@EntityScan(basePackages = {"com.example.entity", "com.example.entity.library"}) // 엔티티 위치
@EnableJpaRepositories(basePackages = {"com.example.repository"}) // 저장소 위치
public class Boot20230427Application extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Boot20230427Application.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(Boot20230427Application.class, args);
	}

    

}
