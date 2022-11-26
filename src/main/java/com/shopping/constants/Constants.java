package com.shopping.constants;

import java.math.BigDecimal;

public class Constants {
	
   public final String PasswordLengthERROR = "Password lenght should be equal or greater than 8";
   public final String UsernameAlreadyExistsError = "User Name already exists please choose another one";
   public final String RegisterQuery = "INSERT INTO Auth(id, user_name, password) VALUES (?,?,?)";
   public final String UserNameExtractQuery = "select * from Auth where user_name = ";
   public final String UserNameLengthERROR = "User Name length should be greater than or equal to 6";
   public final String AddProductQuery = "INSERT INTO Product(id, name, stock, price, currency, brand, category) VALUES (?,?,?,?,?,?,?)";
   public final String Separator = "|";
   
}
