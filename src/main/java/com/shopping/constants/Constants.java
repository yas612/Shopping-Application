package com.shopping.constants;

public class Constants {
	
   public final String PasswordLengthERROR = "Password lenght should be equal or greater than 8";
   public final String EmailAlreadyExistsError = "Email already exists please choose another one";
   public final String verficationCodeError = "Verfication code doesn't match";
   public final String RegisterQuery = "INSERT INTO Auth(id, first_name, last_name, password, email, code, valid) VALUES (?,?,?,?,?,?,?)";
   public final String VerifyQuery = "UPDATE Auth SET code = 'VERIFIED', valid= 'TRUE' WHERE id =";
   public final String EmailExtractQuery = "select * from Auth where email =";
   public final String EmailNotValidERROR = "Email is not valid";
   public final String AddProductQuery = "INSERT INTO Product(id, name, stock, price, currency, brand, category) VALUES (?,?,?,?,?,?,?)";
   public final String UpdateProductQuery = "UPDATE Product SET name=?, stock=?, price=?, currency=?, brand=?, category=? WHERE id=";
   public final String DeleteProductQuery = "DELETE FROM Product WHERE id=";
   public final String FetchAllProductQuery = "select * from Product";
   public final String FetchProductByNameQuery = "select * from Product WHERE name="; 
   public final String FetchProductByBrandQuery = "select * from Product WHERE brand="; 
   public final String FetchProductByCategoryQuery = "select * from Product WHERE category="; 
   public final String FetchAllAuthQuery = "select * from Auth";
   public final String verifyURL = "localhost:8080/shoppingApp/auth/";
   public String mailContent = "Dear [[name]],<br>"
           + "Please click the link below to verify your registration:<br>"
           + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
           + "Thank you,<br>"
           + "Shopping Application.";
   public final String Separator = "|";
   
}
