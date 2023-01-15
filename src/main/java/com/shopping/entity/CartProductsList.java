package com.shopping.entity;

import java.util.List;

public class CartProductsList {
	
	private List<CartIndividualProduct> cartProducts;
	
	public CartProductsList() {
		
	}

	public List<CartIndividualProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartIndividualProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public CartProductsList(List<CartIndividualProduct> cartProducts) {
		super();
		this.cartProducts = cartProducts;
	}
	
	

}
