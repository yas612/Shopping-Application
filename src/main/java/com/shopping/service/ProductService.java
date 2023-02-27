package com.shopping.service;

import java.sql.SQLException;
import java.util.List;
import com.shopping.entity.Product;

public interface ProductService {
	
	public void addProduct(List<Product> product) throws SQLException;
	public Boolean productValidation(List<Product> product);
	public void updateProduct(List<Product> product)throws SQLException;
	public void deleteProduct(int id)throws SQLException;
	public List<Product> getAllProducts()throws SQLException;
	public List<Product> fetchProductByName(String name)throws SQLException;
	public List<Product> fetchProductByBrand(String brand)throws SQLException;
	public List<Product> fetchProductByCategory(String category)throws SQLException;
	public List<Product> sortProductByPriceDesc()throws SQLException;
	public List<Product> sortProductByPriceAsc()throws SQLException;

}
