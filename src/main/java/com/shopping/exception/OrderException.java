package com.shopping.exception;

public class OrderException extends Exception{
	
private static final long serialVersionUID = -2903268500100321707L;
	/**
	 * Parameterized constructor
	 * @param message
	 */
	public OrderException(String message)
	{
		super(message);
	}

}
