package com.shopping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.entity.Auth;
import com.shopping.repository.ShoppingAppRepository;


@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	ShoppingAppRepository repository;

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public Auth register(Auth auth) {
		log.info("Successfully registered");
		return repository.save(auth);
		
	}

	@Override
	public Auth login(Auth auth) {
		// TODO Auto-generated method stub
		return null;
	}

}
