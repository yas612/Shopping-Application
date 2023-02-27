package com.shopping.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.shopping.constants.Constants;
import com.shopping.entity.Auth;
import com.shopping.entity.Roles;
import com.shopping.exception.AuthException;
import com.shopping.rowmapper.AuthRowMapper;
import net.bytebuddy.utility.RandomString;
import java.util.Random;   


@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
     
    @Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Constants constants = new Constants();
	
	Set<Roles> roles = new HashSet<Roles>();
	
	Random random = new Random();   
	
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	//To register in the application
	@Override
	public void register(Auth auth,String siteURL) throws UnsupportedEncodingException, MessagingException, AuthException, SQLException{
			
		Auth authCheck = findUserByEmail(auth);
		
		if(!(authCheck==null)) {
			log.error(constants.EmailAlreadyExistsError);
			throw new AuthException(constants.EmailAlreadyExistsError);
		}
		
		
		if(authCheck==null) {
			
		if(auth.getPassword().length()<8)
		{
			log.error(constants.PasswordLengthERROR);
		throw new AuthException(constants.PasswordLengthERROR);
		}
		
		 String encodedPassword = passwordEncoder.encode(auth.getPassword());
		    auth.setPassword(encodedPassword);
		     
		    String randomCode = RandomString.make(64);
		    auth.setVerification_code(randomCode);
		    auth.setValid(false);
		
	     jdbcTemplate.update(constants.RegisterQuery, new Object[] {auth.getId(), auth.getFirstName(), auth.getLastName(), auth.getPassword(), auth.getEmail(), auth.getVerification_code(), auth.isValid()});
	     log.info("A verfication code is sent to the mail");
	     
	     sendVerificationEmail(auth, siteURL);
		}
		
		
	}

	//Method for logging in
	@Override
	public Boolean login(Auth auth) throws SQLException {
		
		Auth authLogin = findUserByEmail(auth);
		if(!(authLogin == null)) {
		String fetchedEmail = authLogin.getEmail();
		String fetchedPassWord = authLogin.getPassword();
		Boolean valid = authLogin.isValid();
		
		boolean isPasswordMatch = passwordEncoder.matches(auth.getPassword(), fetchedPassWord);
		
		if(!valid) {
			log.error("Plese complete the verfication process to proceed further");
			return false;
		}
		
		if((auth.getEmail().equals(fetchedEmail)) && (isPasswordMatch) && (valid))
		{
			log.info("Authentication successful");
			return true;
			
		}
		
		}
		log.error("Authentication failed.");
		return false;
		
	}

	
	//To fetch user by email
	  public Auth findUserByEmail(Auth auth) throws SQLException {
		  
		  String eMailQuery = constants.EmailExtractQuery+"'"+auth.getEmail()+"'";
		
	 List<Auth> FetchedAuthObject = jdbcTemplate.query(eMailQuery, new AuthRowMapper());
		  
	 if(CollectionUtils.isEmpty(FetchedAuthObject)) {
		return null;
	 }
		   return FetchedAuthObject.get(0); 
		  
		   }
	  
	  //To send the verfication email
	  private void sendVerificationEmail(Auth auth, String siteURL)
		        throws MessagingException, UnsupportedEncodingException {
		    String toAddress = auth.getEmail();
		    String fromAddress = "test6121@gmail.com";
		    String senderName = "Shopping Application";
		    String subject = "Please verify your registration";
		    String content = constants.mailContent;
		     
		    MimeMessage message = mailSender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		     
		    helper.setFrom(fromAddress, senderName);
		    helper.setTo(toAddress);
		    helper.setSubject(subject);
		     
		    content = content.replace("[[name]]", auth.getFirstName()+" "+auth.getLastName());
		   
		    String verifyURL = siteURL + "/shoppingApp/auth/verify/"+auth.getEmail()+"?code=" + auth.getVerification_code();
		     
		    content = content.replace("[[URL]]", verifyURL);
		     
		    helper.setText(content, true);
		     
		    mailSender.send(message);
		     
		}
	  
	  //Verfication mail for merchant account.
	  public void sendMerchantVerificationEmail(Auth auth, String siteURL)
		        throws MessagingException, UnsupportedEncodingException {
		    String toAddress = auth.getEmail();
		    String fromAddress = "test6121@gmail.com";
		    String senderName = "Shopping Application";
		    String subject = "Please verify your registration";
		    String content = constants.mailContent;
		     
		    MimeMessage message = mailSender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		     
		    helper.setFrom(fromAddress, senderName);
		    helper.setTo(toAddress);
		    helper.setSubject(subject);
		     
		    content = content.replace("[[name]]", auth.getFirstName()+" "+auth.getLastName());
		   // localhost:8080/shoppingApp/auth/
		    String verifyURL = siteURL + "/shoppingApp/auth/verify/merchantUser/"+auth.getEmail()+"?code=" + auth.getVerification_code();
		     
		    content = content.replace("[[URL]]", verifyURL);
		     
		    helper.setText(content, true);
		     
		    mailSender.send(message);
		     
		}
	  
	  public boolean verify(String verificationCode, String mail) throws SQLException {
		  
		    List<Auth> FetchedObject = getAllAuth().stream().filter(auth1 -> auth1.getEmail().equals(mail)).collect(Collectors.toList());
		    		
		    if(CollectionUtils.isEmpty(FetchedObject)) {
		    	log.error("No account found with this email.");
		    	return false;
		    }
		     
		    else {
		    	Auth oneUser = FetchedObject.get(0);
		
		    	if(oneUser.getVerification_code().equals(verificationCode)) {
		    		
		    		int id = random.nextInt(100000);   
		    		
		    		jdbcTemplate.update(constants.DefaultRoleInsertQuery, new Object[] {id, oneUser.getEmail(), constants.DefaultRole});
		    		
		    	  jdbcTemplate.update(constants.VerifyQuery+oneUser.getId());
		          
		        return true;
		    	}
		    	log.error(constants.verficationCodeError);
		    	return false;
		    	
		    }
		     
		}

	@Override
	public List<Auth> getAllAuth() throws SQLException {
		List<Auth> allusers =  jdbcTemplate.query(constants.FetchAllAuthQuery, new AuthRowMapper());
		if(CollectionUtils.isEmpty(allusers)) {
			return null;
		}
		return allusers;
	}


	@Override
	public boolean merchantVerify(String verificationCode, String mail) throws SQLException{
		 List<Auth> FetchedObject = getAllAuth().stream().filter(auth1 -> auth1.getEmail().equals(mail)).collect(Collectors.toList());
 		
		    if(CollectionUtils.isEmpty(FetchedObject)) {
		    	log.error("No account found with this email.");
		    	return false;
		    }
		     
		    else {
		    	Auth oneUser = FetchedObject.get(0);
		
		    	if(oneUser.getVerification_code().equals(verificationCode)) {
		    		
		    		int id = random.nextInt(100000);   
		    		
		    		jdbcTemplate.update(constants.DefaultRoleInsertQuery, new Object[] {id, oneUser.getEmail(), constants.MerchantRole});
		    		
		    	  jdbcTemplate.update(constants.VerifyQuery+oneUser.getId());
		          
		        return true;
		    	}
		    	log.error(constants.verficationCodeError);
		    	return false;
		    	
		    }
	}

	@Override
	public void merchantRegister(Auth auth, String siteURL) throws UnsupportedEncodingException, MessagingException, AuthException, SQLException {
           Auth authCheck = findUserByEmail(auth);
		
		if(!(authCheck==null)) {
			log.error(constants.EmailAlreadyExistsError);
			throw new AuthException(constants.EmailAlreadyExistsError);
		}
		
		
		if(authCheck==null) {
			
	
		if(auth.getPassword().length()<8)
		{
			log.error(constants.PasswordLengthERROR);
		throw new AuthException(constants.PasswordLengthERROR);
		}
		
		 String encodedPassword = passwordEncoder.encode(auth.getPassword());
		    auth.setPassword(encodedPassword);
		     
		    String randomCode = RandomString.make(64);
		    auth.setVerification_code(randomCode);
		    auth.setValid(false);
		
	     jdbcTemplate.update(constants.RegisterQuery, new Object[] {auth.getId(), auth.getFirstName(), auth.getLastName(), auth.getPassword(), auth.getEmail(), auth.getVerification_code(), auth.isValid()});
	     log.info("A verfication code is sent to the mail");
	     
	     sendMerchantVerificationEmail(auth, siteURL);
		}
		
	}
	 
  

}