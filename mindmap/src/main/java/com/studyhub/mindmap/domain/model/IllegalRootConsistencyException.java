package com.studyhub.mindmap.domain.model;

public class IllegalRootConsistencyException extends RuntimeException {
    public IllegalRootConsistencyException(String reason) {
        super("Root inconsistent. Reason: %s = null is not allowed".formatted(reason));
    }
}
