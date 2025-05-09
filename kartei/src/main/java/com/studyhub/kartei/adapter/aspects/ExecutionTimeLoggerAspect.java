package com.studyhub.kartei.adapter.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLoggerAspect {

	Logger log = LoggerFactory.getLogger(ExecutionTimeLoggerAspect.class);

	@Around("@annotation(com.studyhub.kartei.adapter.aspects.LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		log.info("Method '%s' executed in %s ms".formatted(joinPoint.getSignature().toShortString(), duration));
		return result;
	}
}
