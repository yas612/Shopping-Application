package com.shopping.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	
	//To add product
	@GetMapping(path="/add/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String addProductToCart(@PathVariable("id") int id) throws CartException, JsonProcessingException, SQLException {
		log.info("Add to cart product process started.");
		service.addtoCart(id);
		log.info("Add to cart product process completed.");
		return "Successfully added to Cart";
		
	}
	
	//To remove a particular product
	@GetMapping(path="/remove/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String removeFromCart(@PathVariable("id") int id) throws CartException, JsonProcessingException, SQLException {
		log.info("Remove from cart process started.");
		service.removeProductFromCart(id);
		log.info("Add to cart product process started.");
		return "Successfully removed product from Cart";
		
	}
	
	//To empty the cart
	@GetMapping(path="/empty",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String emptyCart() throws CartException, JsonProcessingException, SQLException {
		log.info("Empty cart process started.");
		service.emptyCart();
		log.info("Add to cart product process started.");
		return "Successfully emptied cart";
		
	}
	
	
	
	/*@PostMapping(path="/update",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String UpdateProductToCart(@RequestBody int id, int count, int cartId) throws CartException {
		service.updateCart(id, count, cartId);
		return "Successfully updated product";
		
	} */

}
