package com.studyhub.kartei.domain.model;

/**
 * If the aggregate consistency is harmed, throw this exception with a reason, why the consistency was harmed.
 */
public class AggregateInconsistencyException extends RuntimeException {
	public AggregateInconsistencyException(String message) {
		super(message);
	}
}
