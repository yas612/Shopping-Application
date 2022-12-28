package com.shopping.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.shopping.ProductRowMapper;
import com.shopping.constants.Constants;
import com.shopping.entity.Product;



@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	Constants constants = new Constants();
	
	public List<Product> NoteligibleProduct = new ArrayList<Product>();
	public List<String> rejectedProduct = new ArrayList<String>();
	

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public void addProduct(List<Product> product) {
		
		productValidation(product);
		product.removeAll(NoteligibleProduct);
		
		rejectedProduct.forEach(eachRejectedProduct -> {
			log.info(eachRejectedProduct);
		});
		
		product.forEach(everyProduct -> {
			jdbcTemplate.update(constants.AddProductQuery, new Object[] {everyProduct.getId(), everyProduct.getName(), everyProduct.getStock(), everyProduct.getPrice(), everyProduct.getCurrency(), everyProduct.getBrand(), everyProduct.getCategory()});
		});
		
		log.info("No of Products added = "+product.size()+" No of Products rejected = "+rejectedProduct.size());
		
	}
	
	@Override
	public void updateProduct(List<Product> product) {
		productValidation(product);
		product.removeAll(NoteligibleProduct);
		
		rejectedProduct.forEach(eachRejectedProduct -> {
			log.info(eachRejectedProduct);
		});
		
		product.forEach(eachProduct -> {
			jdbcTemplate.update(constants.UpdateProductQuery+eachProduct.getId(),  new Object[] { eachProduct.getName(), eachProduct.getStock(), eachProduct.getPrice(), eachProduct.getCurrency(), eachProduct.getBrand(), eachProduct.getCategory()});
		});
		
		log.info("No of Products updated = "+product.size()+" No of Products rejected = "+rejectedProduct.size());
		
	}
	
	@Override
	public void deleteProduct(int id) {
		
		jdbcTemplate.update(constants.DeleteProductQuery+id);
	}
	
	@Override
	public List<Product> getAllProducts() {
		List<Product> products =  jdbcTemplate.query(constants.FetchAllProductQuery, new ProductRowMapper());
		if(products.isEmpty()) {
			return null;
		}
		return products;
	}
	
	@Override
	public List<Product> fetchProductByName(String name) {
		List<Product> fetchedProductByName = getAllProducts();
		if(fetchedProductByName.isEmpty()) {
			log.info("No Product found with this name : "+name);
			return null;
		}
		
		return fetchedProductByName.stream().filter((product -> product.getName().contains(name))).collect(Collectors.toList());
	}
	
	@Override
	public List<Product> fetchProductByBrand(String brand) {
		List<Product> fetchedProductByBrand = getAllProducts();
		if(fetchedProductByBrand.isEmpty()) {
			log.info("No Product found with this name : "+brand);
			return null;
		}
		
		return fetchedProductByBrand.stream().filter((product -> product.getName().contains(brand))).collect(Collectors.toList());
	}

	@Override
	public List<Product> fetchProductByCategory(String category) {
		List<Product> fetchedProductByCategory = getAllProducts();
		if(fetchedProductByCategory.isEmpty()) {
			log.info("No Product found with this name : "+category);
			return null;
		}
		
		return fetchedProductByCategory.stream().filter((product -> product.getName().contains(category))).collect(Collectors.toList());
		
	}
	
	@Override
	public List<Product> sortProductByPriceDesc() {
		List<Product> products = getAllProducts();
		if(products.isEmpty()) {
			return null;
		}
	    
		Comparator<Product> compareByPrice = (Product p1, Product p2) -> p1.getPrice().compareTo(p2.getPrice());
		Collections.sort(products,compareByPrice.reversed());
		
		return products;
		
	}

	@Override
	public List<Product> sortProductByPriceAsc() {
		List<Product> products = getAllProducts();
		if(products.isEmpty()) {
			return null;
		}
		
		Comparator<Product> compareByPrice = (Product p1, Product p2) -> p1.getPrice().compareTo(p2.getPrice());
		Collections.sort(products,compareByPrice);
		
		return products;
		
	}
	

	@Override
	public Boolean productValidation(List<Product> product) {
		
		if(product.isEmpty()) {
			log.error("The products entered is empty.");
			return false;
		}
		
		product.forEach(eachProduct -> {
			if(eachProduct.getName().isEmpty()) {
				NoteligibleProduct.add(eachProduct);
				log.error("Name field is empty for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : Name is empty");
			}
			
			if(eachProduct.getBrand().isEmpty()) {
				NoteligibleProduct.add(eachProduct);
				log.error("Brand field is empty for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : Brand is empty");
			}
			
			if(eachProduct.getPrice().doubleValue() < 0.1) {
				NoteligibleProduct.add(eachProduct);
				log.error("The value of price is 0 or negative for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : The value of price is 0 or negative");
			}
			
			if(eachProduct.getStock() < 1) {
				NoteligibleProduct.add(eachProduct);
				log.error("The value of stock is 0 or negative for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : The value of stock is 0 or negative");
			}
			
			if(eachProduct.getCurrency().isEmpty()) {
				NoteligibleProduct.add(eachProduct);
				log.error("Currency field is empty for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : Currency field is empty");
			}
			
			if(eachProduct.getCategory().isEmpty()) {
				NoteligibleProduct.add(eachProduct);
				log.error("Category field is empty for product with id "+eachProduct.getId());
				rejectedProduct.add(eachProduct.toString()+constants.Separator+" STATUS : REJECTED "+constants.Separator+" Reason : Category field is empty");
			}
			
		    
		});
		return true;
	}

	

}
