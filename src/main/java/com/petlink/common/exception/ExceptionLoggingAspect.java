package com.petlink.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    // @ExceptionHandler 어노테이션에 대한 대상으로 동작하는 로깅 AOP
    @Around("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public Object logException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            // joinPoint.proceed() 메서드는 실제로 대상 메서드를 실행함
            result = joinPoint.proceed();
        } catch (Exception exception) {
            // 만약 대상 메서드 실행 중 예외가 발생하면, 로그에 에러 메시지를 남기고,
            // 다시 예외를 던져서 원래의 ExceptionHandler에서 처리하도록 함
            log.error("Exception occurred", exception);
            throw exception;
        }
        // 대상 메서드 실행 결과를 반환함
        return result;
    }
}

