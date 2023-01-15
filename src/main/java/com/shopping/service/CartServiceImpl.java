package com.shopping.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.shopping.CartRowMapper;
import com.shopping.ProductRowMapper;
import com.shopping.constants.Constants;
import com.shopping.entity.Cart;
import com.shopping.entity.CartIndividualProduct;
import com.shopping.entity.Product;
import com.shopping.exception.CartException;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Constants constants = new Constants();
	
	private BigDecimal cartTotal = new BigDecimal(0);
	
	Random random = new Random(); 
	int count =0;
	
	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
	
	Cart cart = new Cart();
	List<CartIndividualProduct> allProductofCart = new ArrayList<CartIndividualProduct>();
	List<CartIndividualProduct> checkExistingCartIndividualProducts = new ArrayList<CartIndividualProduct>();
	List<Cart> FetchedProductofCart = new ArrayList<Cart>();
	ObjectMapper mapper = new ObjectMapper();
	String jsonString="";

	@Override
	public void addtoCart(int id) throws CartException, JsonProcessingException {
	
			List<Product> products =  jdbcTemplate.query(constants.FetchAllProductById+id, new ProductRowMapper());
			if(products.isEmpty()) {
				log.error("Product not found");
		    	 throw new CartException("PRODUCT NOT FOUND");
			}
		     Product requiredProduct = new Product();
		     requiredProduct = products.get(0);
		     if(requiredProduct.getStock()==0) {
		    	 log.error("Product is currently OUT OF STOCK");
		    	 throw new CartException("Product is currently OUT OF STOCK");
		     }
		     
		     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		     String username;

		     if (principal instanceof UserDetails) {
		       username = ((UserDetails)principal).getUsername();
		     } else {
		        username = principal.toString();
		     }
		     
		     CartIndividualProduct cartProduct = new CartIndividualProduct();
		     cartProduct.setId(requiredProduct.getId());
		     cartProduct.setProductName(requiredProduct.getName());
		     cartProduct.setProductBrandName(requiredProduct.getBrand());
		     cartProduct.setProductCategory(requiredProduct.getCategory());
		     cartProduct.setProductPrice(requiredProduct.getPrice());
		     cartProduct.setProductCount(1);
		     cartProduct.setProductCurrency(requiredProduct.getCurrency());
		     allProductofCart.add(cartProduct);
 
		     FetchedProductofCart = jdbcTemplate.query(constants.ExtractCartQuery+"'"+username+"'", new CartRowMapper());
		     
		     if(!FetchedProductofCart.isEmpty()) {
	    	
		    	 Cart oldCart = FetchedProductofCart.get(0);
				 
		         String[] eachProductArray = oldCart.getAllProductsInCart().split("\\"+constants.Separator);
		         List<String> eachProductList = Arrays.asList(eachProductArray);

		    	Set<CartIndividualProduct> list = new LinkedHashSet<CartIndividualProduct>();
		    	eachProductList.forEach((product) -> {
		    		 Gson gson = new Gson();
		    		
		    		 CartIndividualProduct cartProduct1 = gson.fromJson(product, CartIndividualProduct.class); 
		    	
		    		list.add(cartProduct1);
		    	});
		    		
		    	//To check in the existing products
		    	list.forEach((check) -> {
		    		
		    		if(check.getId()==id) {
		    			check.setProductCount(check.getProductCount()+1);
		    			oldCart.setBagTotal(oldCart.getBagTotal().add(cartProduct.getProductPrice()));
		    		}
		    		
		    		if(!(check.getId()==id)) {
		    		    count = count+1;
		    		}
		    	
		    	});
		    	
		    	if(count==list.size()) {
		    		list.add(cartProduct);
		    	}
		    	
		    	count =0;
                  
		    	 StringBuilder val = new StringBuilder();
		    list.forEach(update -> {
		    	try {
		    		
		    	jsonString = mapper.writeValueAsString(update);
		    	}
		    	 catch(JsonProcessingException e) {
					  
					   e.printStackTrace();  }
		    	val.append(jsonString+constants.Separator);
		    	
		    
		    });
         jdbcTemplate.update(constants.UpdateCartQuery+"'"+username+"'",val, oldCart.getBagTotal());
		         
		         jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()-1)+"WHERE id="+requiredProduct.getId());
		     }
		     
		     
		     if(FetchedProductofCart.isEmpty()) {
		     cartTotal = cartTotal.add(cartProduct.getProductPrice().multiply(BigDecimal.valueOf(cartProduct.getProductCount())));
		     
				try {
					jsonString = mapper.writeValueAsString(cartProduct);
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}
		     
				 Cart toBeUpdated = new Cart();
			     toBeUpdated.setUsername(username);
			     toBeUpdated.setAllProductsInCart(jsonString);
			     toBeUpdated.setBagTotal(cartTotal);
	     
		     int cart1 =  jdbcTemplate.update((con) -> {
	                final PreparedStatement ps = con.prepareStatement(constants.AddToCartQuery);
	                ps.setString(1, toBeUpdated.getUsername());
	                ps.setString(2, toBeUpdated.getAllProductsInCart()+constants.Separator);
	                ps.setBigDecimal(3, cartTotal);
	                return ps;
	            });
		     
		     jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()-1)+"WHERE id="+requiredProduct.getId());
		     }
		     
		}

	

	@Override
	public void updateCart(int id, int count, int cartId) {
		
		allProductofCart.forEach( oneProduct -> {
			if(oneProduct.getId()==id) {
				oneProduct.setProductCount(count);
				cartTotal.subtract(oneProduct.getProductPrice().multiply(BigDecimal.valueOf(oneProduct.getProductCount())));
				cartTotal = cartTotal.add(oneProduct.getProductPrice().multiply(BigDecimal.valueOf(count)));
				
			}
		});
		
		jdbcTemplate.update(constants.UpdateCartQuery+id,allProductofCart.toString(),cartTotal);

	}

	@Override
	public void removeProductFromCart(int id) {
		// TODO Auto-generated method stub

	}

}
