package com.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import com.shopping.entity.Cart;


public class CartRowMapper implements RowMapper<Cart> {

	@Override
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cart cart = new Cart();
		cart.setUsername(rs.getString("username"));
		cart.setAllProductsInCart(rs.getString("products"));
		cart.setBagTotal(rs.getBigDecimal("total"));
		return cart;
	}

}
