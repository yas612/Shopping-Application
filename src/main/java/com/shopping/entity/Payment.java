package com.shopping.entity;

import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "payment")
public class Payment {
	
	@Id
	@Column(name="paymentid")
	private int transactionId;
	
	
	@Column(name="paymentdate")
	private Date transactionDate;
	
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="amount")
	private BigDecimal amount;
	
	
	@Column(name="status")
	private String paymentStatus;
	
	
}
