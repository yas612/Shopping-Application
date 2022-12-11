package com.shopping.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.shopping.entity.Auth;

public interface AuthService {
	
	public void register(Auth auth,String siteURL) throws UnsupportedEncodingException, MessagingException;
	public Boolean login(Auth auth);
	public Auth findUserByEmail(Auth auth);
	public boolean verify(String verificationCode, String mail);
	public List<Auth> getAllAuth();
	
}
