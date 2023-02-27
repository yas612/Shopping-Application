package com.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.shopping.entity.Product;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setStock(rs.getInt("stock"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setBrand(rs.getString("brand"));
		product.setCategory(rs.getString("category"));
		return product;
	}

}
