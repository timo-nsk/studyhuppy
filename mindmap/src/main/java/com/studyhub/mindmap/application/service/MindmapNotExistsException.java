package com.studyhub.mindmap.application.service;

public class MindmapNotExistsException extends RuntimeException {
	public MindmapNotExistsException(String message) {
		super(message);
	}
}
