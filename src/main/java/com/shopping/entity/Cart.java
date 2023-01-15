package com.shopping.entity;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONObject;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "cart")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Cart {
		
	@Id
	@Column(name="username")
	private String username;
	
	//@Type(type = "json")
	//@Column(columnDefinition = "jsonb")
	@Column(name = "products")
	private String allProductsInCart;
	
	@Column(name="total")
	private BigDecimal bagTotal;
	
	public Cart() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAllProductsInCart() {
		return allProductsInCart;
	}

	public void setAllProductsInCart(String allProductsInCart) {
		this.allProductsInCart = allProductsInCart;
	}

	public BigDecimal getBagTotal() {
		return bagTotal;
	}

	public void setBagTotal(BigDecimal bagTotal) {
		this.bagTotal = bagTotal;
	}

	public Cart(String username, String allProductsInCart, BigDecimal bagTotal) {
		super();
		this.username = username;
		this.allProductsInCart = allProductsInCart;
		this.bagTotal = bagTotal;
	}

	@Override
	public String toString() {
		return "Cart [username=" + username + ", allProductsInCart=" + allProductsInCart + ", bagTotal=" + bagTotal
				+ "]";
	}

		

}
