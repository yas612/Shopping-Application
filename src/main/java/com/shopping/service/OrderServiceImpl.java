package com.shopping.service;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.shopping.constants.Constants;
import com.shopping.constants.OrderStatus;
import com.shopping.entity.Order;
import com.shopping.exception.OrderException;
import com.shopping.rowmapper.OrderRowMapper;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Constants constants = new Constants();
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	//Method to retrieve all orders.
	@Override
	public List<Order> getAllOrder() throws OrderException, SQLException {
		log.info("Started the order retrieving process.");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		List<Order> allOrders = jdbcTemplate.query(constants.FetchAllOrderQuery.concat("'"+username+"'"), new OrderRowMapper());
		
		if(CollectionUtils.isEmpty(allOrders)) {
			log.error("No Orders placed");
			throw new OrderException("No Orders placed");
		}
		log.info("Orders retrieved.");
		return allOrders;
	}

	//Method to cancel an order.
	@Override
	public String deleteOrder(int id)throws OrderException, SQLException {
		log.info("Starting cancelling process.");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		jdbcTemplate.update(constants.CancelOrderByIdQuery,OrderStatus.CANCELLED.toString(),username,id);
		log.info("Order with id : ".concat(String.valueOf(id)).concat(" has been cancelled"));
		return "Order with id : ".concat(String.valueOf(id)+" has been cancelled");
	}

}
