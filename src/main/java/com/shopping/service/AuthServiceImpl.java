package com.shopping.service;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.shopping.AuthRowMapper;
import com.shopping.constants.Constants;
import com.shopping.entity.Auth;
import com.shopping.entity.User;
import com.shopping.exception.AuthRequestException;


@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private ShoppingAppMailService notificationService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	User user;
	
	Constants constants = new Constants();
	

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public void register(Auth auth) {
			
		Auth authCheck = findUserByUserName(auth);
		
		if(!(authCheck==null)) {
			throw new AuthRequestException(constants.UsernameAlreadyExistsError);
		}
		
		if(authCheck==null) {
			
			if(auth.getUserName().length()<6) {
				throw new AuthRequestException(constants.UserNameLengthERROR);
			}
	
		if(auth.getPassword().length()<8)
		{
		throw new AuthRequestException(constants.PasswordLengthERROR);
		}
		
		user.setEmailAddress("mryas612@gmail.com");
		//notificationService.sendEmail(user);
		
	     jdbcTemplate.update(constants.RegisterQuery, new Object[] {auth.getId(), auth.getUserName(), auth.getPassword()});
	     log.info("Successfully registered");
		}
		
		
	}

	@Override
	public Boolean login(Auth auth) {
		
		Auth authLogin = findUserByUserName(auth);
		if(!(authLogin == null)) {
		String fetchedUserName = authLogin.getUserName();
		String fetchedPassWord = authLogin.getPassword();
		 
		if((auth.getUserName().equals(fetchedUserName)) && (auth.getPassword().equals(fetchedPassWord)))
		{
			return true;
		}
		}
		return false;
		
	}

	
	  public Auth findUserByUserName(Auth auth) {
		  
		  String userNameQuery = constants.UserNameExtractQuery+"'"+auth.getUserName()+"'";
		
	 List<Auth> FetchedAuthObject = jdbcTemplate.query(userNameQuery, new AuthRowMapper());
		  
	 if(FetchedAuthObject.isEmpty()) {
		return null;
	 }
		   return FetchedAuthObject.get(0); 
		  
		   }
	 
  

}
