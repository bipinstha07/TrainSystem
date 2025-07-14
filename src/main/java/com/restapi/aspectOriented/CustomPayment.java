package com.restapi.aspectOriented;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomPayment {
    private final static Logger logger = LoggerFactory.getLogger(CustomPayment.class);

    @Before("@annotation(com.restapi.aspectOriented.CustomPaymentAnnotation)")
    public void before(JoinPoint joinPoint){
        logger.info("Before Payment Executing");
    }

    @AfterReturning(pointcut = "@annotation(com.restapi.aspectOriented.CustomPaymentAnnotation)",
    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        logger.info("Payment Success :"+ joinPoint.getSignature().getName() + result);
    }

    @AfterThrowing(
            pointcut = "@annotation(com.restapi.aspectOriented.CustomPaymentAnnotation)",
            throwing = "ex"
    )
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Payment Failed: " + joinPoint.getSignature().getName());
        logger.error("Exception: " + ex.getMessage());
    }


}
