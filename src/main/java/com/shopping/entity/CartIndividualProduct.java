package com.shopping.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartIndividualProduct {
	
	@Id
	private int id;
	private String productName;
	private String productBrandName;
	private String productCategory;
	private BigDecimal productPrice;
	private String productCurrency;
	private int productCount;
	
	@JsonProperty("id")
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("productName")
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@JsonProperty("productBrandName")
	public String getProductBrandName() {
		return productBrandName;
	}
	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}
	
	@JsonProperty("productCategory")
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	@JsonProperty("productPrice")
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	
	@JsonProperty("productCurrency")
	public String getProductCurrency() {
		return productCurrency;
	}
	public void setProductCurrency(String productCurrency) {
		this.productCurrency = productCurrency;
	}
	
	@JsonProperty("productCount")
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public CartIndividualProduct(int id, String productName, String productBrandName, String productCategory,
			BigDecimal productPrice, String productCurrency, int productCount) {
		super();
		this.id = id;
		this.productName = productName;
		this.productBrandName = productBrandName;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
		this.productCurrency = productCurrency;
		this.productCount = productCount;
	}	

}
