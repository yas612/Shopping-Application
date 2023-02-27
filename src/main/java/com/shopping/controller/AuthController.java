package com.shopping.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.entity.Auth;
import com.shopping.exception.AuthException;
import com.shopping.service.AuthServiceImpl;

@RestController
@RequestMapping("/shoppingApp/auth")
public class AuthController {
	
	@Autowired
	AuthServiceImpl service;
	
	//Noraml user regiter method
	
	@PostMapping(path="/register",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String registerInApplication(@RequestBody Auth auth, HttpServletRequest request) throws AuthException, UnsupportedEncodingException, MessagingException, SQLException{
		service.register(auth,  getSiteURL(request));
		return "verification code has been sent to the registered mail.";
	}
	
	//Merchant user register method
	
	@PostMapping(path="/registerAsMerchant",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String registerInApplicationAsMerchantUser(@RequestBody Auth auth, HttpServletRequest request) throws AuthException, UnsupportedEncodingException, MessagingException, SQLException{
		service.merchantRegister(auth,  getSiteURL(request));
		return "verification code has been sent to the registered mail.";
		
	}
	
	//User login method
	
	@PostMapping(path="/login",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String loginToApplication(@RequestBody Auth auth) throws AuthException, SQLException{
		Boolean response = service.login(auth);
		if(response)
			return "Login successful";
		else
		return "Login failed check the entered User Name and Password";
		
	}
	
	//To get the URL of the site
	
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
	
	//Normal user verify method
	
	@GetMapping("/verify/{mail}")
	public String verifyUser(@Param("code") String code, @PathVariable("mail") String mail) throws SQLException {
	    if (service.verify(code,mail)) {
	        return "verify_success";
	    } else {
	        return "verify_fail";
	    }
	}
	
	//Merchant user verify method
	
	@GetMapping("/verify/merchantUser/{mail}")
	public String verifyMerchantUser(@Param("code") String code, @PathVariable("mail") String mail) throws SQLException {
	    if (service.merchantVerify(code,mail)) {
	        return "verify_success";
	    } else {
	        return "verify_fail";
	    }
	}
	

}
