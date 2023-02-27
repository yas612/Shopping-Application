package com.shopping.exception;

public class AuthException extends Exception{
	
private static final long serialVersionUID = -2903268500100321707L;
	/**
	 * Parameterized constructor
	 * @param message
	 */
	public AuthException(String message)
	{
		super(message);
	}
}
