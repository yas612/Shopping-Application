package com.shopping.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	
	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
	

	
	
	Cart cart = new Cart();
	List<CartIndividualProduct> allProductofCart = new ArrayList<CartIndividualProduct>();


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
		    
		   // List<Cart> cart =  jdbcTemplate.query(constants.ExtractCartQuery+"'"+username+"'",new CartRowMapper());
		    
		     Map<String, Object> cart =  jdbcTemplate.queryForMap(constants.ExtractCartQuery+"'"+username+"'");
		     
		     if(!cart.isEmpty()) {
		    	 
		    	 CartIndividualProduct cartProduct = new CartIndividualProduct();
			     cartProduct.setId(requiredProduct.getId());
			     cartProduct.setProductName(requiredProduct.getName());
			     cartProduct.setProductBrandName(requiredProduct.getBrand());
			     cartProduct.setProductCategory(requiredProduct.getCategory());
			     cartProduct.setProductPrice(requiredProduct.getPrice());
			     cartProduct.setProductCount(1);
			     cartProduct.setProductCurrency(requiredProduct.getCurrency());
			     allProductofCart.add(cartProduct);
			     //cart.setAllProduct(allProductofCart);
			     
			     cartTotal = cartTotal.add(cartProduct.getProductPrice().multiply(BigDecimal.valueOf(cartProduct.getProductCount())));
			     
			     ObjectMapper mapper = new ObjectMapper();
			      //Converting the Object to JSONString
			      
			     JSONArray jsonArray = new JSONArray();
			     
			    Object id1 =  cart.get("products");
			    
			     allProductofCart.forEach((toBeConverTedToJSON) -> {
			    	 String jsonString="";
						try {
							jsonString = mapper.writeValueAsString(toBeConverTedToJSON);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						jsonArray.put(toBeConverTedToJSON.getId(), jsonString);
						
			     });;
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
		     //cart.setAllProduct(allProductofCart);
		     
		     cartTotal = cartTotal.add(cartProduct.getProductPrice().multiply(BigDecimal.valueOf(cartProduct.getProductCount())));
		     //cart.setBagTotal(cartTotal);
		     //int carId = random.nextInt(100000); 
		     //cart.setId(carId);
		     ObjectMapper mapper = new ObjectMapper();
		      //Converting the Object to JSONString
		      
		     JSONArray jsonArray = new JSONArray();
		     List<JSONObject> elementsList = new ArrayList<JSONObject>();
		     
		    
		     allProductofCart.forEach((toBeConverTedToJSON) -> {
		    	 String jsonString="";
					try {
						jsonString = mapper.writeValueAsString(toBeConverTedToJSON);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jsonArray.put(toBeConverTedToJSON.getId(), jsonString);
					
		     });;
		     
		    for(int i=0;i<8;i++) {
		    	jsonArray.remove(i);
		    }
		   
		  /*   allProductofCart.forEach(product -> {
		    	 JSONObject formDetailsJson = new JSONObject();
		    	 String jsonString="";
				try {
					jsonString = mapper.writeValueAsString(product);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonArray.put(product.getId(), jsonString);
				
		        // formDetailsJson.put(String.valueOf(product.getId()), jsonString);
		       //  formDetailsJson.put("name", );
		        // elementsList.add(formDetailsJson);
		    	 
		     });*/
		   
		     System.out.println(jsonArray.length());
		     System.out.println(jsonArray.length());
		     System.out.println(allProductofCart);
		     
		    // if(!cart.isEmpty()) {
		    	 
		    	// Cart existingCart = new Cart();
		    	 //existingCart = cart.get(0);
		    	 //cartTotal = existingCart.getBagTotal();
		    	 //cartTotal.add(requiredProduct.getPrice());
		    	// JSONObject formDetailsJso =  existingCart.getAllProductsInCart().getJSONObject(String.valueOf(requiredProduct.getId()));
		    	 //formDetailsJso.get
		    	 
		     //}

		   // responseDetailsJson.put("products", elementsList);
		    
		     //List<JSONObject> elementsList1 = new ArrayList<JSONObject>();
		    
			  // Map<String, Object> fetchedCart = new HashMap<String, Object>();
			  // System.out.println(cart.get(cart));
			 //  System.out.println(cart.get("products"));
			 //  System.out.println(cart.get("total"));
			   //CartIndividualProduct cartProduct1 = new CartIndividualProduct();
			  // cartProduct1 = (CartIndividualProduct) cart.get("products");
			   //System.out.println(cartProduct1.getProductCategory());
			 //  System.out.println( cart.get("products").toString());
			   
			   
			 
			  // JSONObject responseDetailsJsonOld = new JSONObject(); 
			   //responseDetailsJsonOld.get(username);
			  // ObjectMapper mapper2 = new JsonMapper();
			  // JsonNode json = mapper2.readTree(cart.get("products").toString());
			 //  String name = json.get("productCategory'\'").asText();
			  // System.out.println("new"+name);
			   
			     // String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cart.get("products"));
			      //System.out.println(jsonStr);
			      System.out.println("Deserializing JSON to Object:");
			     // CartIndividualProduct emp2 = mapper.readValue(jsonStr, CartIndividualProduct.class);
			     // System.out.println(emp2.getProductBrandName());
			  // System.out.println(cart.get));
			   /*if(!cart.isEmpty()) {
				   fetchedCart = cart.get(0);
				   JSONObject responseDetailsJsonOld = new JSONObject(); 
				   responseDetailsJsonOld = (JSONObject) fetchedCart.get(username);
				   
				   System.out.println(cart.get(0));
				   System.out.println(fetchedCart.get(username));
				   ObjectMapper mapper = new ObjectMapper();
				      String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fetchedCart.get("products"));
				      System.out.println(jsonStr);
				      System.out.println("Deserializing JSON to Object:");
				      Cart emp2 = mapper.readValue(jsonStr, Cart.class);
				      System.out.println(emp2.getUsername()+emp2.getAllProductsInCart()+emp2.getBagTotal());
				   }*/
			   
		    //System.out.println(jsonArray);
		     //jdbcTemplate.update(constants.AddToCartQuery, cart.getId(), jsonArray, cart.getBagTotal(), username);
		    // jdbcTemplate.update("INSERT INTO cart(id, products, total, username) VALUES ("+cart.getId()+","+"'"+jsonArray+"'"+","+cart.getBagTotal()+","+"'"+ username+"'"+")");
		   jdbcTemplate.update("INSERT INTO cart(username, products, total) VALUES ("+"'"+ username+"'"+","+"'"+jsonArray+"'"+","+cartTotal+")");
		    //System.out.println(jsonArray);
		    // int toBeUpdatedStock = requiredProduct.getStock()-1;
		     
		    // jdbcTemplate.update("UPDATE product SET stock="+toBeUpdatedStock+"WHERE id="+requiredProduct.getId());
		     
		     
		     
		     
			log.info(username);
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
