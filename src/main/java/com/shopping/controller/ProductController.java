package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//Merchant can add the product
	
	@PostMapping(path="/add",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String addProduct(@RequestBody List<Product> product) {
		service.addProduct(product);
		return "Successfully added product";
		
	}
	
	//Merchant can update the product
	
	@PostMapping(path="/update",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String updateProduct(@RequestBody List<Product> product) {
		service.updateProduct(product);
		return "Successfully added product";
		
	}
	
	//Merchant can delete the product
	
	@DeleteMapping(path="/delete/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
		 try {
		      service.deleteProduct(id);
		      return new ResponseEntity<>("Product was deleted successfully.", HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>("Cannot delete product.", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  }
	
	//To retrieve all the products
	
	@GetMapping(path="/getAllProducts")
	public ResponseEntity<String> getAllProducts(){
		try {
			service.getAllProducts();
			 return new ResponseEntity<>("Producs retrieved successfully.", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Cannot retrieve products.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// To sort the product by price(Low to high)
	
	@GetMapping(path="/getAllProducts/sortByPrice/Asc")
	public List<Product> getAllProductsSortedByPriceAsc(){	
			return service.sortProductByPriceAsc();
	}
	
	// To sort the product by price(High to low)
	
	@GetMapping(path="/getAllProducts/sortByPrice/Dsc")
	public List<Product> getAllProductsSortedByPriceDsc(){
			return service.sortProductByPriceDesc();
	}
	
	// To filter the product based on company name.
	@GetMapping(path="/filter/ByProductName/{name}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public List<Product> filterProductByName(@PathVariable("name") String name) {
		
		     return service.fetchProductByName(name);
	}
	
	//To filter the product based on category.
	@GetMapping(path="/filter/ByProductCategory/{category}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public List<Product> filterProductByCategory(@PathVariable("category") String category) {

		return service.fetchProductByCategory(category);
	}

}
