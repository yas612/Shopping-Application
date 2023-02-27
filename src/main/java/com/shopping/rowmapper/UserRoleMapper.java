package com.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shopping.entity.Roles;

public class UserRoleMapper implements RowMapper<Roles> {
	
	@Override
	public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {

		Roles roles = new Roles();
		roles.setId(rs.getInt("id"));
		roles.setRole(rs.getString("role"));
		roles.setEmail(rs.getString("email"));
		return roles;
		
	}


}
