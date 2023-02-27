package com.shopping.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.mail.MessagingException;
import com.shopping.exception.PaymentException;

public interface PaymentService {
	
	public void pay(String siteURL) throws PaymentException, SQLException;
	public String yesResponse() throws ParseException, PaymentException, UnsupportedEncodingException, MessagingException, SQLException;
	public String noResponse() throws ParseException, SQLException, PaymentException;

}
