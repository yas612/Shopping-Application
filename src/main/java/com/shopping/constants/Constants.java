package com.shopping.constants;

public class Constants {
	
   public final String PasswordLengthERROR = "Password lenght should be equal or greater than 8";
   public final String UsernameAlreadyExistsError = "User Name already exists please choose another one";
   public final String RegisterQuery = "INSERT INTO Auth(id, user_name, password) VALUES (?,?,?)";
   public final String UserNameExtractQuery = "select * from Auth where user_name = ";

}
