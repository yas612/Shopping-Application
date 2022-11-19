package com.shopping.exception;

public class AuthRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthRequestException(String message) {
		super(message);
	}
}
