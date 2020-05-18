package org.liuchuanhung.aop.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

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

        List<Map<Object, Object>> paramTypeToValueMaps = mapParameterValueToType(
                ((MethodSignature) joinPoint.getSignature()).getParameterTypes(),  joinPoint.getArgs());

        LOGGER.info("Method Argument(s): " + paramTypeToValueMaps.toString());
        return proceed;
    }

    @AfterReturning(value = "@annotation(org.liuchuanhung.aop.logging.annotation.Loggable)", returning = "result")
    public void afterReturningLoggable(JoinPoint joinPoint, Object result) {
        if (result != null) {
            LOGGER.info("Response: " + result);
        }
    }

    private List<Map<Object, Object>> mapParameterValueToType(Class[] parameterTypes, Object[] values) {
        List<Map<Object, Object>> paramToValueMaps = new ArrayList<>();
        for (int i=0; i<parameterTypes.length; i++) {
            Map<Object, Object> paramToValueMap = new HashMap<>();
            paramToValueMap.put(parameterTypes[i], values[i]);
            paramToValueMaps.add(paramToValueMap);
        }
        return paramToValueMaps;
    }
}