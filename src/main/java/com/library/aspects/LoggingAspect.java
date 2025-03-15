package com.library.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all methods in the BookService and PatronService
    @Pointcut("execution(* com.library.services.BookService.*(..)) || execution(* com.library.services.PatronService.*(..))")
    public void serviceMethods() {}

    // Around advice to log method execution time and exceptions
    @Around("serviceMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.info("Executing {}.{}", className, methodName);

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Successfully executed {}.{} in {} ms", className, methodName, executionTime);
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.{}: {}", className, methodName, e.getMessage());
            throw e;
        }
    }

    @Pointcut("execution(* com.library.services.BookService.addBook(..))")
public void addBookMethod() {}

@Pointcut("execution(* com.library.services.BookService.updateBook(..))")
public void updateBookMethod() {}

@Around("addBookMethod() || updateBookMethod()")
public Object logBookOperations(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();

    logger.info("Executing {}.{}", className, methodName);

    long startTime = System.currentTimeMillis();
    try {
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("Successfully executed {}.{} in {} ms", className, methodName, executionTime);
        return result;
    } catch (Exception e) {
        logger.error("Exception in {}.{}: {}", className, methodName, e.getMessage());
        throw e;
    }
    }
}