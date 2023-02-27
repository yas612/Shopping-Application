package com.shopping.service;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopping.exception.CartException;

public interface CartService {
	
	public void addtoCart(int id) throws CartException, JsonProcessingException, SQLException;
	//public void updateCart(int id, int count)throws CartException;
	public void removeProductFromCart(int id) throws CartException, SQLException;
	public void emptyCart() throws CartException, SQLException;

}
