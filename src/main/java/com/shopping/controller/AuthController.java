package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.entity.Auth;
import com.shopping.service.AuthServiceImpl;

@RestController
@RequestMapping("/shoppingApp")
public class AuthController {
	
	@Autowired
	AuthServiceImpl service;
	
	@PostMapping(path="/register",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public String add(@RequestBody Auth auth) {
		service.register(auth);
		System.out.println(auth.getUserName()+" "+auth.getPassword());
		return "Successfully reistered in the APP";
		
	}

}
