package com.restapi.aspectOriented;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Create a logger instance for LoggingAspect class
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Around advice: wraps method execution in the specified package
    @Around("execution(* com.restapi.controller.admin.*.*(..))")
    public Object loggerBefore(ProceedingJoinPoint joinPoint) throws Throwable {

        // Log before method execution
        logger.info("Before method execution: " + joinPoint.getSignature().getName());

        // Record start time
        long timeBefore = System.currentTimeMillis();

        // Proceed with actual method execution
        Object result = joinPoint.proceed();

        // Record end time
        long timeAfter = System.currentTimeMillis();

        // Log after method execution
        logger.info("After method execution: " + joinPoint.getSignature().getName());

        // Calculate and log time taken
        long timeTaken = timeAfter - timeBefore;
        logger.info("Time Taken: " + timeTaken + "ms");

        return result;
    }

}
