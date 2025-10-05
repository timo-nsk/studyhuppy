package com.studyhub.kartei.adapter.aspects;

import com.studyhub.kartei.adapter.web.exception.KarteikarteDoesNotExistException;
import com.studyhub.kartei.service.application.KarteikarteService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class KarteikarteAspect {

	private final KarteikarteService karteikarteService;

	public KarteikarteAspect(KarteikarteService karteikarteService) {
		this.karteikarteService = karteikarteService;
	}

	@Around("@annotation(com.studyhub.kartei.adapter.aspects.CheckKarteikarteExists) && args(karteFachId,..)")
	public Object checkIfExists(ProceedingJoinPoint joinPoint, String karteFachId) throws Throwable {
		if (karteikarteService.karteNotExistsById(karteFachId)) {
			System.out.println(karteFachId);
			throw new KarteikarteDoesNotExistException("Karteikarte mit id: '%s' existiert nicht.".formatted(karteFachId));
		}
		return joinPoint.proceed();
	}
}

