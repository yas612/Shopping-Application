package com.shopping;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
//@ComponentScan("com.spring.rest")
public class ShoppingApplication {

		 public static void main(String[] args) {
		        SpringApplication.run(ShoppingApplication.class, args);

	}
	/*
	 * @Autowired Environment environment; private final String URL = "url"; private
	 * final String Driver = "driver"; private final String UserName = "username";
	 * private final String Password = "password";
	 */
		 
		 @Bean
		 DataSource datasource(){
			 DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
			 driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
			 driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/ShoppingApp");
			 driverManagerDataSource.setUsername("postgres");
			 driverManagerDataSource.setPassword("yas612");
			 return driverManagerDataSource;
		 }
		 
		 @Bean
		 public PasswordEncoder passwordEncoder()
		 {
		     return new BCryptPasswordEncoder();
		 }
		 

			/*
			 * @Bean public PersistenceExceptionTranslationPostProcessor
			 * exceptionTranslation() { return new
			 * PersistenceExceptionTranslationPostProcessor(); }
			 */
		   
		 @Bean
		 JdbcTemplate jdbcTemplate() {
			 return new JdbcTemplate(datasource());
		 }

}
