package com.shopping.rowmapper;

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
		auth.setFirstName(rs.getString("first_name"));
		auth.setLastName(rs.getString("last_name"));
		auth.setEmail(rs.getString("email"));
		auth.setVerification_code(rs.getString("code"));
		auth.setValid(rs.getBoolean("valid"));
		return auth;
	}

}
