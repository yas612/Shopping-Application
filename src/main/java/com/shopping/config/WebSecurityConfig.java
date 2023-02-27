package com.shopping.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopping.constants.Constants;



@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	Constants constants = new Constants();
	
	
	  
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception { 

		  auth.jdbcAuthentication().dataSource(datasource)
	      .usersByUsernameQuery(constants.UserVerifyQuery)
	      .authoritiesByUsernameQuery(constants.AuthorityVerifyQuery)
	      .passwordEncoder(passwordEncoder);
	  }
	  
	  @Override protected void configure(HttpSecurity http) throws Exception 
	  {
		 
	  http.csrf().disable().authorizeRequests() 
	  .antMatchers("/shoppingApp/product/getAllProducts").permitAll()
	  .antMatchers("/shoppingApp/product/getAllProducts/sortByPrice/Asc").permitAll()
	  .antMatchers("/shoppingApp/product/getAllProducts/sortByPrice/Dsc").permitAll()
	  .antMatchers("/shoppingApp/product/filter/ByProductName/**").permitAll()
	  .antMatchers("/shoppingApp/product/filter/ByProductCategory/**").permitAll()
	  .antMatchers("/shoppingApp/product/getAllProducts").permitAll()
	  .antMatchers("/shoppingApp/auth/verify/merchantUser/**").permitAll()
	  .antMatchers("/shoppingApp/auth/verify/**").permitAll()
	  .antMatchers("/shoppingApp/auth/register").permitAll()
	  .antMatchers("/shoppingApp/auth/registerAsMerchant").permitAll()
	  .antMatchers("/shoppingApp/auth/login").permitAll()
	  .antMatchers("/shoppingApp/product/getAllProducts/add").hasAnyAuthority("MERCHANT", "ADMIN")
	  .antMatchers("/shoppingApp/product/getAllProducts/update").hasAnyAuthority("MERCHANT", "ADMIN")
	  .antMatchers("/shoppingApp/product/getAllProducts/delete").hasAnyAuthority("MERCHANT", "ADMIN")
	  .antMatchers("/shoppingApp/cart/add/**").hasAnyAuthority("USER", "ADMIN")
	  .antMatchers("/shoppingApp/payment/pay**").hasAnyAuthority("USER", "ADMIN")
	  .antMatchers("/shoppingApp/order**").hasAnyAuthority("USER", "ADMIN")
	  .anyRequest().authenticated().and().formLogin().permitAll().and()
	  .logout().permitAll().and().exceptionHandling().accessDeniedPage("/403")
	  .and().httpBasic();
	  
	  }
	  
}