package com.shopping.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfig {
	
	 @Value("${db.class}")
	    private String driverClassName;
	 
	 @Value("${db.url}")
	    private String dBURL;
	 
	 @Value("${db.username}")
	    private String dBUserName;
	 
	 @Value("${db.password}")
	    private String dBPassWord;
	
	 @Bean
	 DataSource datasource(){
		 DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		 driverManagerDataSource.setDriverClassName(ShoppingAppEncryption.decrypt(driverClassName));
		 driverManagerDataSource.setUrl(ShoppingAppEncryption.decrypt(dBURL));
		 driverManagerDataSource.setUsername(ShoppingAppEncryption.decrypt(dBUserName));
		 driverManagerDataSource.setPassword(ShoppingAppEncryption.decrypt(dBPassWord));
		 return driverManagerDataSource;
	 }

}
