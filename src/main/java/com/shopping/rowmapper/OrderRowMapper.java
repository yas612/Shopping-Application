package com.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shopping.entity.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	    Order order = new Order();
	    order.setOrderId(rs.getInt("orderid"));
	    order.setProductDetails(rs.getString("products"));
	    order.setEmail(rs.getString("email"));
	    order.setOrderStatus(rs.getString("status"));
		return order;
		
	}

}
