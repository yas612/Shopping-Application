package com.shopping;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ShoppingApplication {
	
	@Autowired
	DataSource datasource;

		 public static void main(String[] args) {
		        SpringApplication.run(ShoppingApplication.class, args);
		        }
		 
	 
		 @Bean
		 public PasswordEncoder passwordEncoder()
		 {
		     return new BCryptPasswordEncoder();
		 }
		 
		   
		 @Bean
		 JdbcTemplate jdbcTemplate(DataSource datasource) {
			 return new JdbcTemplate(datasource);
		 }

}
