package com.shopping.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shopping.constants.Constants;
import com.shopping.entity.Cart;
import com.shopping.entity.CartIndividualProduct;
import com.shopping.entity.Product;
import com.shopping.exception.CartException;
import com.shopping.rowmapper.CartRowMapper;
import com.shopping.rowmapper.ProductRowMapper;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Constants constants = new Constants();
	
	private BigDecimal cartTotal = new BigDecimal(0);
	BigDecimal updatedPrice = null;
	BigDecimal addToCartPrice = null;
	 int updatedProductCount = 0;
	
	Random random = new Random(); 
	public Boolean decider=true;
	public Boolean cartRemoveDecider=true;
	
	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
	
	Cart cart = new Cart();
	List<CartIndividualProduct> allProductofCart = new ArrayList<CartIndividualProduct>();
	List<CartIndividualProduct> checkExistingCartIndividualProducts = new ArrayList<CartIndividualProduct>();
	List<Cart> FetchedProductofCart = new ArrayList<Cart>();
	ObjectMapper mapper = new ObjectMapper();
	String jsonString="";

	//Method to adding product
	
	@Override
	public void addtoCart(int id) throws CartException, JsonProcessingException , SQLException{
		addToCartPrice = null;
	
		log.info("Cart adding process started.");
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
		     
		     if(!CollectionUtils.isEmpty(FetchedProductofCart)) {
	    	
		    	 Cart oldCart = FetchedProductofCart.get(0);
				 
		         String[] eachProductArray = oldCart.getAllProductsInCart().split("\\"+constants.Separator);
		         List<String> eachProductList = Arrays.asList(eachProductArray);

		    	Set<CartIndividualProduct> list = new LinkedHashSet<CartIndividualProduct>();
		    	eachProductList.forEach((product) -> {
		    		 Gson gson = new Gson();
		    		
		    		 CartIndividualProduct cartProduct1 = gson.fromJson(product, CartIndividualProduct.class); 
		    	
		    		 if(cartProduct1 != null) {
		    		list.add(cartProduct1);
		    		 }
		    	});
		    	
		    	System.out.println(list);
		    	System.out.println(list.size());
		    	System.out.println(list.isEmpty());
		    	
		    	
		    	if((list != null) || !list.isEmpty()) {	
		    	//To check in the existing products
		    	list.forEach((check) -> {
		    		
		    		if(check.getId()==id) {
		    			check.setProductCount(check.getProductCount()+1);
		    			addToCartPrice = (oldCart.getBagTotal().add(cartProduct.getProductPrice()));
		    			oldCart.setBagTotal(addToCartPrice);
		    			decider=false;
		    			
		    		}
		    		
		    	});
		    	
		    	
		    	if(decider) {
		    		list.add(cartProduct);
		    		addToCartPrice = (oldCart.getBagTotal().add(cartProduct.getProductPrice()));
	    			oldCart.setBagTotal(addToCartPrice);
	    			log.info("Existing Product in cart added => ".concat(cartProduct.toString()));
		    		
		    	}
		    	}
		    	
		    	else {
		    		list.add(cartProduct);
		    		log.info("NEW Product in cart added => ".concat(cartProduct.toString()));
		    	}
		    	
		    	decider=true;
		    	
                  
		    	 StringBuilder val = new StringBuilder();
		    list.forEach(update -> {
		    	try {
		    		
		    	jsonString = mapper.writeValueAsString(update);
		    	}
		    	 catch(JsonProcessingException e) {
					  
					   e.printStackTrace();  }
		    	val.append(jsonString+constants.Separator);
		    	
		    
		    });
		    log.info("Added product to cart");
         jdbcTemplate.update(constants.UpdateCartQuery+"'"+username+"'",val, oldCart.getBagTotal());
		         
         log.info("Updaing product count in product inventory");
		         jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()-1)+"WHERE id="+requiredProduct.getId());
		     }
		     
		     
		     if(CollectionUtils.isEmpty(FetchedProductofCart)) {
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
	     
			     log.info("First product is addded to cart => ".concat(toBeUpdated.toString()));
		     int cart1 =  jdbcTemplate.update((con) -> {
	                final PreparedStatement ps = con.prepareStatement(constants.AddToCartQuery);
	                ps.setString(1, toBeUpdated.getUsername());
	                ps.setString(2, toBeUpdated.getAllProductsInCart()+constants.Separator);
	                ps.setBigDecimal(3, cartTotal);
	                return ps;
	            });
		     
		     log.info("Updating product count in inventory");
		     jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()-1)+"WHERE id="+requiredProduct.getId());
		     }
		     log.info("Cart adding process ended");
		}

	
  /*
	@Override
	public void updateCart(int id, int count) throws CartException {
	     
		List<Product> products =  jdbcTemplate.query(constants.FetchAllProductById+id, new ProductRowMapper());
		if(products.isEmpty()) {
			log.error("Product not found");
	    	 throw new CartException("PRODUCT NOT FOUND");
		}
	     Product requiredProduct = new Product();
	     requiredProduct = products.get(0);
	     
	     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
	     
	     if(count == 0) {
	    	 throw new CartException("The count cannot be 0");
			
	     }
	     
	     int updateProductCount =0;
	     
	     

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
	    	
	    	CartIndividualProduct toBeRemoved = new CartIndividualProduct();
	    		
	    	//To check in the existing products
	    	list.forEach((check) -> {
	    		
	    		if(check.getId()==id) {
	    			if((check.getProductCount()+count)<0) {
	    				throw new CartException("There is a mismatch in the count mentioned & the existing count of the product");
	    			}
	    			check.setProductCount(check.getProductCount()+count);
	    			oldCart.setBagTotal(oldCart.getBagTotal().add(check.getProductPrice().multiply(BigDecimal.valueOf(count))));
	    			toBeRemoved = check;
	    			cartUpdateDecider=true;
	    		}
	    		
	    	});
	    	
	    	if(cartUpdateDecider) {
	    		list.remove(toBeRemoved);
	    	}
	    	else {
	    		throw new CartException("The product with id = "+id+" was not found in cart");
	    	}
	    	
	    	cartUpdateDecider=false;
	    	
              
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
	         
	         jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()+updateProductCount)+"WHERE id="+requiredProduct.getId());
	     }
	     
	     
	  
	}
	*/

	//Method to remove a product from the cart
	
	@Override
	public void removeProductFromCart(int id) throws CartException, SQLException {
		log.info("Remove particular product from cart process started");
		List<Product> products =  jdbcTemplate.query(constants.FetchAllProductById+id, new ProductRowMapper());
		if(products.isEmpty()) {
			log.error("Error in updating product count in product table");
	    	 throw new CartException("Error in updating product count in product table");
		}
	     Product requiredProduct = new Product();
	     requiredProduct = products.get(0);
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;
	     updatedPrice = null;
	     updatedProductCount = 0;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
	   

	     FetchedProductofCart = jdbcTemplate.query(constants.ExtractCartQuery+"'"+username+"'", new CartRowMapper());
	     
	     if(FetchedProductofCart.isEmpty()){
	    	 log.error("Cart not found");
	    	 throw new CartException("Cart not found");
	     }
	     
	     if(!FetchedProductofCart.isEmpty()) {
    	
	    	 Cart oldCart = FetchedProductofCart.get(0);
	    	 
	    	 if(oldCart.getAllProductsInCart().isEmpty()){
	    		 log.error("Cart is Empty");
		    	 throw new CartException("Cart is Empty");
		     }
			 
	         String[] eachProductArray = oldCart.getAllProductsInCart().split("\\"+constants.Separator);
	         List<String> eachProductList = Arrays.asList(eachProductArray);

	    	Set<CartIndividualProduct> list = new LinkedHashSet<CartIndividualProduct>();
	    	Set<CartIndividualProduct> listCopy = new LinkedHashSet<CartIndividualProduct>();
	    	eachProductList.forEach((product) -> {
	    		 Gson gson = new Gson();
	    		
	    		 CartIndividualProduct cartProduct1 = gson.fromJson(product, CartIndividualProduct.class); 
	    	
	    		list.add(cartProduct1);
	    		listCopy.add(cartProduct1);
	    	});
	    		updatedPrice = oldCart.getBagTotal();
	    	//To check in the existing products
	    	list.forEach((check) -> {
	    		
	    		if(check.getId()==id) {
	    			listCopy.remove(check);
	    			updatedPrice = oldCart.getBagTotal().subtract(check.getProductPrice().multiply(BigDecimal.valueOf(check.getProductCount())));
	    			updatedProductCount = check.getProductCount();
	    			cartRemoveDecider=false;
	    		
	    		}
	    		
	    	});
	    	
	    	if(cartRemoveDecider) {
	    		throw new CartException("Product not found in cart, so can't able to remove it. Try adding the product.");
	    	}
	    	cartRemoveDecider=true;

              
	    	 StringBuilder val = new StringBuilder();
	    	 listCopy.forEach(update -> {
	    	try {
	    		
	    	jsonString = mapper.writeValueAsString(update);
	    	}
	    	 catch(JsonProcessingException e) {
				  
				   e.printStackTrace();  
				   }
	    	val.append(jsonString+constants.Separator);
	    	
	    
	    });
	    	 
	    	 log.info("Product with id : ".concat(String.valueOf(id)).concat(" is removed."));
     jdbcTemplate.update(constants.UpdateCartQuery+"'"+username+"'",val, updatedPrice);
	        
	        
            log.info("Updating the product count in inventory");
	     jdbcTemplate.update("UPDATE product SET stock="+(requiredProduct.getStock()+updatedProductCount)+"WHERE id="+id);

	}
	     log.info("Remove particular product from cart process Ended");
	}

	//Method to empty cart.

	@Override
	public void emptyCart() throws CartException, SQLException {
		log.info("Remove entire product from cart process started");
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;
	    BigDecimal emptyPrice = new BigDecimal("0.0");

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		String val = "";
		log.info("Cart is made empty");
		jdbcTemplate.update(constants.UpdateCartQuery+"'"+username+"'",val, emptyPrice);
		log.info("Remove entire product from cart process ended");
	}

}
