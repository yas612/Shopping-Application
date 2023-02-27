package com.shopping.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import javax.mail.MessagingException;
import com.shopping.entity.Auth;
import com.shopping.exception.AuthException;

public interface AuthService {
	
	public void register(Auth auth,String siteURL) throws UnsupportedEncodingException, MessagingException, AuthException,SQLException;
	public void merchantRegister(Auth auth,String siteURL) throws UnsupportedEncodingException, MessagingException, AuthException,SQLException;
	public Boolean login(Auth auth) throws SQLException;
	public Auth findUserByEmail(Auth auth)throws SQLException;
	public boolean verify(String verificationCode, String mail)throws SQLException;
	public boolean merchantVerify(String verificationCode, String mail)throws SQLException;
	public List<Auth> getAllAuth() throws SQLException;
	
}
