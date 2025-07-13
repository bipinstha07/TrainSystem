package com.restapi.aspectOriented;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.restapi.controller.admin.*.*(..))")
    public  Object loggerBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method exectuion :"+ joinPoint.getSignature().getName() );
        long timebefore = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeafter = System.currentTimeMillis();
        logger.info("After method Execution :"+ joinPoint.getSignature().getName());
        long timeTaken = timeafter-timebefore;
        logger.info("Time Taken : "+ timeTaken +"ms");
        return  result;
    }
}
