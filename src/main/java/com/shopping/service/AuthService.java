package com.shopping.service;

import com.shopping.entity.Auth;

public interface AuthService {
	
	public void register(Auth auth);
	public Boolean login(Auth auth);
	public Auth findUserByUserName(Auth auth);
	
}
