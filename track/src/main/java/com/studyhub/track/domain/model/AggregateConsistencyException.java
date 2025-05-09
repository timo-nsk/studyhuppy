package com.studyhub.track.domain.model;

public class AggregateConsistencyException extends RuntimeException{
	public AggregateConsistencyException(String message) {
		super(message);
	}
}
