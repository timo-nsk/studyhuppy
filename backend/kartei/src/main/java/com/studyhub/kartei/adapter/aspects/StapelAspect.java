package com.studyhub.kartei.adapter.aspects;

import com.studyhub.kartei.adapter.web.exception.StapelDoesNotExistException;
import com.studyhub.kartei.service.application.StapelService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StapelAspect {

	private final StapelService stapelService;

	public StapelAspect(StapelService stapelService) {
		this.stapelService = stapelService;
	}

	@Around("@annotation(com.studyhub.kartei.adapter.aspects.CheckStapelExists) && args(karteiSetId,..)")
	public Object checkIfExists(ProceedingJoinPoint joinPoint, String karteiSetId) throws Throwable {
		if (stapelService.stapelNotExists(karteiSetId)) {
			throw new StapelDoesNotExistException("Stapel mit id: '%s' nicht gefunden.".formatted(karteiSetId));
		}
		return joinPoint.proceed();
	}
}
