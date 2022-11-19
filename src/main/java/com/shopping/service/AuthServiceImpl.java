package com.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.shopping.AuthRowMapper;
import com.shopping.constants.Constants;
import com.shopping.entity.Auth;
import com.shopping.exception.AuthRequestException;


@Service
public class AuthServiceImpl implements AuthService {
	
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Constants constants = new Constants();
	

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public Auth register(Auth auth) {
		
		/*
		 * Auth userCheck = findUserByUserName(auth); String name =
		 * userCheck.getUserName(); System.out.println(name); if(name == null) {
		 * System.out.println(name); }
		 */
		
		String userNameCheck = findUserByUserName(auth);
		
		if(!(userNameCheck == null) && userNameCheck.equalsIgnoreCase(auth.getUserName())){
			throw new AuthRequestException(constants.UsernameAlreadyExistsError);
		}
	
		/*
		 * if(name.equals(auth.getUserName()) && !name.isEmpty()) { throw new
		 * AuthRequestException(constants.UsernameAlreadyExistsError); }
		 */
		
		if(auth.getPassword().length()<7)
		{
		throw new AuthRequestException(constants.PasswordLengthERROR);
		}
		log.info("Successfully registered");
	     jdbcTemplate.update(constants.RegisterQuery, new Object[] {auth.getId(), auth.getUserName(), auth.getPassword()});
	     return auth;
		
		
	}

	@Override
	public Auth login(Auth auth) {
		// TODO Auto-generated method stub
		return null;
	}

	
	  public String findUserByUserName(Auth auth) {
		  Auth o = null; 
		  
		  String f = "select * from Auth where user_name = "+"'"+auth.getUserName()+"'";
		 
	// o =  jdbcTemplate.queryForObject("select * from Auth where user_name = ?", new Object[]
	  //{auth.getUserName()}, new AuthRowMapper()); 
	 List<Auth> a = jdbcTemplate.query(f, new AuthRowMapper());
		  
	 if(a.isEmpty()) {
		 return null;
	 }
		   return o.getUserName(); 
		  
		   }
	 
  

}
