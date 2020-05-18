package org.liuchuanhung.aop.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggableAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableAspect.class);

    @Around("@annotation(org.liuchuanhung.aop.logging.annotation.Loggable)")
    public Object loggable(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        LOGGER.info("Execution Time: " + executionTime + "ms");
        LOGGER.info("Calling Class: " + joinPoint.getStaticPart().getSignature().getDeclaringTypeName()); //calling class
        LOGGER.info("Calling Method: " + joinPoint.getStaticPart().getSignature().getName()); //calling method
        LOGGER.info("Arguments Passed: " + Arrays.toString(joinPoint.getArgs()));
        return proceed;
    }

    @AfterReturning(value = "@annotation(org.liuchuanhung.aop.logging.annotation.Loggable)", returning = "result")
    public void afterReturningLoggable(JoinPoint joinPoint, Object result) {
        LOGGER.info("Response: " + result);
    }
}