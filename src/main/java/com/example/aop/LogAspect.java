package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {

    // com.example.controller.jpa의 모든 Controller의 모든메소드 반응
    // com.example.repository의 모든 Repository의 모든메소드 반응
    @Around("execution(* com.example.controller.jpa.*Controller.*(..)) or execution(* com.example.repository.*Repository.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String clsname = joinPoint.getSignature().getDeclaringTypeName();
        String metname = joinPoint.getSignature().getName();

        String result = "";
        if(clsname.contains("Controller")) {
            result = "type : Controller => ";
        }
        else if(clsname.contains("Service")) {
            result = "type : Service => ";
        }
        else if(clsname.contains("Repository")) {
            result = "type : Repository => ";
        }
        else if(clsname.contains("Mapper")) {
            result = "type : Mapper => ";
        }

        result += clsname + "=>" + metname + "()";
        log.info(result);
        return joinPoint.proceed();        
    }
    
}
