package com.restapi.aspectOriented;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomAspect {
    private static final Logger logger = LoggerFactory.getLogger(CustomAspect.class);

    @After("@annotation(com.restapi.aspectOriented.CustomAopAnnotation)")
    public void logginAfter(JoinPoint joinPoint){
        logger.info("Custom AOP annotation :"+ joinPoint.getSignature().getName());
    }

}
