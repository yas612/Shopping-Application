package com.shopping.entity;

import java.math.BigDecimal;
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
@Table(name = "cart")
public class Cart {
		
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name = "products")
	private String allProductsInCart;
	
	@Column(name="total")
	private BigDecimal bagTotal;

}
