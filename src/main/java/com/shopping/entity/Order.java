package com.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
	
	@Id
	@Column(name="orderid")
	private int orderId;
	
	@Column(name="email")
	private String email;
	
	@Column(name="products")
	private String productDetails;
	
	@Column(name="status")
	private String orderStatus;

	
}
