package com.shopping.service;

import java.util.List;

import com.shopping.entity.Product;

public interface ProductService {
	
	public void addProduct(List<Product> product);
	public Boolean productValidation(List<Product> product);

}
