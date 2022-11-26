package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.entity.Product;
import com.shopping.service.ProductServiceImpl;

@RestController
@RequestMapping("/shoppingApp/product")
public class ProductController {
	
	@Autowired
	ProductServiceImpl service;
	
	@PostMapping(path="/add",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String registerInApplication(@RequestBody List<Product> product) {
		service.addProduct(product);
		return "Successfully added product";
		
	}

}
