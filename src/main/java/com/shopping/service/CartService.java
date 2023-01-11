package com.shopping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopping.exception.CartException;

public interface CartService {
	
	public void addtoCart(int id) throws CartException, JsonProcessingException;
	public void updateCart(int id, int count, int cartId);
	public void removeProductFromCart(int id);

}
