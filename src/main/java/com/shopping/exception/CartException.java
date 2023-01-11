package com.shopping.exception;

public class CartException extends Exception{
	
private static final long serialVersionUID = -2903268500100321707L;
	/**
	 * Parameterized constructor
	 * @param message
	 */
	public CartException(String message)
	{
		super(message);
	}

}
