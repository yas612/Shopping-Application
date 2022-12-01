package com.shopping.constants;

public class Constants {
	
   public final String PasswordLengthERROR = "Password lenght should be equal or greater than 8";
   public final String UsernameAlreadyExistsError = "User Name already exists please choose another one";
   public final String RegisterQuery = "INSERT INTO Auth(id, user_name, password) VALUES (?,?,?)";
   public final String UserNameExtractQuery = "select * from Auth where user_name = ";
   public final String UserNameLengthERROR = "User Name length should be greater than or equal to 6";
   public final String AddProductQuery = "INSERT INTO Product(id, name, stock, price, currency, brand, category) VALUES (?,?,?,?,?,?,?)";
   public final String UpdateProductQuery = "UPDATE Product SET name=?, stock=?, price=?, currency=?, brand=?, category=? WHERE id=";
   public final String DeleteProductQuery = "DELETE FROM Product WHERE id=";
   public final String FetchAllProductQuery = "select * from Product";
   public final String FetchProductByNameQuery = "select * from Product WHERE name="; 
   public final String FetchProductByBrandQuery = "select * from Product WHERE brand="; 
   public final String FetchProductByCategoryQuery = "select * from Product WHERE category="; 
   public final String Separator = "|";
   
}
