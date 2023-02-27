package com.shopping.service;

import java.sql.SQLException;
import java.util.List;

import com.shopping.entity.Order;
import com.shopping.exception.OrderException;

public interface OrderService {
	
	public List<Order> getAllOrder()throws OrderException, SQLException;
	public String deleteOrder(int id)throws OrderException, SQLException;

}
