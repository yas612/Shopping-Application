package com.shopping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shopping.entity.Auth;

public class AuthRowMapper implements RowMapper<Auth> {

	@Override
	public Auth mapRow(ResultSet rs, int rowNum) throws SQLException {
		Auth auth = new Auth();
		auth.setId(rs.getInt("id"));
		auth.setPassword(rs.getString("password"));
		auth.setUserName(rs.getString("user_name"));
		return auth;
	}

}
