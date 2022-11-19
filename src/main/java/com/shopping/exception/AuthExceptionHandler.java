package com.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AuthExceptionHandler {
	
	@ExceptionHandler(value= {AuthRequestException.class})
	public ResponseEntity<Object> handleProfileRequestException(AuthRequestException e){
		
		HttpStatus badRequest=HttpStatus.BAD_REQUEST;
	    AuthException apiException=new AuthException(e.getMessage());
		return new ResponseEntity<Object>(apiException,badRequest);
	}

}
