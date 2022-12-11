package com.shopping.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.entity.Auth;
import com.shopping.exception.AuthRequestException;
import com.shopping.service.AuthServiceImpl;

@RestController
@RequestMapping("/shoppingApp/auth")
public class AuthController {
	
	@Autowired
	AuthServiceImpl service;
	
	@Autowired
    private MessageSource messageSource;
	
	@PostMapping(path="/register",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String registerInApplication(@RequestBody Auth auth, HttpServletRequest request) throws AuthRequestException, UnsupportedEncodingException, MessagingException{
		service.register(auth,  getSiteURL(request));
		return "verification code has been sent to the registered mail.";
		
	}
	
	@PostMapping(path="/login",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String loginToApplication(@RequestBody Auth auth) throws AuthRequestException{
		Boolean response = service.login(auth);
		if(response)
			return "Login successful";
		else
		return "Login failed check the entered User Name and Password";
		
	}
	
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
	
	@GetMapping("/verify/{mail}")
	public String verifyUser(@Param("code") String code, @PathVariable("mail") String mail) {
	    if (service.verify(code,mail)) {
	        return "verify_success";
	    } else {
	        return "verify_fail";
	    }
	}
	

}
