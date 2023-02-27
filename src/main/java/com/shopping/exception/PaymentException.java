package com.shopping.exception;

public class PaymentException extends Exception{
	
private static final long serialVersionUID = -2903268500100321707L;
	/**
	 * Parameterized constructor
	 * @param message
	 */
	public PaymentException(String message)
	{
		super(message);
	}
}
