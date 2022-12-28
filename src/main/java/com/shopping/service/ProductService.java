package com.shopping.service;

import java.util.List;
import com.shopping.entity.Product;

public interface ProductService {
	
	public void addProduct(List<Product> product);
	public Boolean productValidation(List<Product> product);
	public void updateProduct(List<Product> product);
	public void deleteProduct(int id);
	public List<Product> getAllProducts();
	public List<Product> fetchProductByName(String name);
	public List<Product> fetchProductByBrand(String brand);
	public List<Product> fetchProductByCategory(String category);
	public List<Product> sortProductByPriceDesc();
	public List<Product> sortProductByPriceAsc();

}
