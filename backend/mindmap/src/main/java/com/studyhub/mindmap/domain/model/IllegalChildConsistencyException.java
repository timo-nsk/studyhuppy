package com.studyhub.mindmap.domain.model;

public class IllegalChildConsistencyException extends RuntimeException {
    public IllegalChildConsistencyException(String reason) {
        super("Root inconsistent. Reason: %s = null is not allowed".formatted(reason));
    }
}
