package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopping.exception.CartException;
import com.shopping.service.CartServiceImpl;

@RestController
@RequestMapping("/shoppingApp/cart")
public class CartController {
	
	@Autowired
	CartServiceImpl service;
	
	@GetMapping(path="/add/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String addProductToCart(@PathVariable("id") int id) throws CartException, JsonProcessingException {
		service.addtoCart(id);
		return "Successfully added to Cart";
		
	}
	
	@GetMapping(path="/remove/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String removeFromCart(@PathVariable("id") int id) throws CartException, JsonProcessingException {
		service.removeProductFromCart(id);
		return "Successfully added to Cart";
		
	}
	
	@GetMapping(path="/empty",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String emptyCart() throws CartException, JsonProcessingException {
		service.emptyCart();
		return "Successfully added to Cart";
		
	}
	
	
	
	/*@PostMapping(path="/update",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String UpdateProductToCart(@RequestBody int id, int count, int cartId) throws CartException {
		service.updateCart(id, count, cartId);
		return "Successfully updated product";
		
	} */

}
