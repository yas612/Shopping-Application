package com.shopping.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.entity.Order;
import com.shopping.exception.OrderException;
import com.shopping.service.OrderServiceImpl;

@RestController
@RequestMapping("/shoppingApp/order")
public class OrderController {
	
	@Autowired
	OrderServiceImpl service;
	
	//To retrieve all orders.
	@GetMapping(path="/orders",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public List<Order> retriveAllOrder() throws OrderException, SQLException{
		return service.getAllOrder();
	}
	
	//To cancel an order.
	@DeleteMapping(path="/delete/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String delete(@PathVariable("id") int id) throws OrderException, SQLException{
		service.deleteOrder(id);
		return "order with id : ".concat(id+" is Cancelled.");
		}

}
