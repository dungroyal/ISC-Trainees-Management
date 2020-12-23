package com.ISC.project.exception;

public class ResourseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourseNotFoundException() {
		this("Resource not found!");
	}

	public ResourseNotFoundException(String message) {
		this(message, null);
	}

	public ResourseNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
