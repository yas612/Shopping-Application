package com.shopping.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.exception.PaymentException;
import com.shopping.service.PaymentServiceImpl;

@RestController
@RequestMapping("/shoppingApp/payment")
public class PaymentController {
	
	@Autowired
	PaymentServiceImpl service;
	
	//To pay process
	@GetMapping(path="/pay",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String registerInApplicationAsMerchantUser(HttpServletRequest request) throws PaymentException, UnsupportedEncodingException, MessagingException, SQLException{
		service.pay(getSiteURL(request));
		return "verification code has been sent to the registered mail.";
		
	}
	

	//To confirm payment
	@GetMapping("/ack/yes")
	public String verifyPayemntAsyes() throws UnsupportedEncodingException, ParseException, PaymentException, MessagingException, SQLException {
	        service.yesResponse();
	    	return "Payment approved";
	    
	}
	
	//To Deny payment
	@GetMapping("/ack/no")
	public String verifyPaymentAsNo() throws ParseException, SQLException, PaymentException {
	        service.noResponse();
	    	return "Payment Declined";
	    
	}
	
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  

}
