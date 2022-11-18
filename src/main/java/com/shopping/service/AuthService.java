package com.shopping.service;

import com.shopping.entity.Auth;

public interface AuthService {
	
	public Auth register(Auth auth);
	public Auth login(Auth auth);
}
