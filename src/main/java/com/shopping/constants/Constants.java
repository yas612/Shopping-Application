package com.shopping.constants;

public class Constants {
	
   public final String PasswordLengthERROR = "Password lenght should be equal or greater than 8";
   public final String EmailAlreadyExistsError = "Email already exists please choose another one";
   public final String verficationCodeError = "Verfication code doesn't match";
   public final String RegisterQuery = "INSERT INTO auth(id, first_name, last_name, password, email, code, valid) VALUES (?,?,?,?,?,?,?)";
   public final String VerifyQuery = "UPDATE auth SET code = 'VERIFIED', valid= 'TRUE' WHERE id =";
   public final String EmailExtractQuery = "select * from Auth where email =";
   public final String EmailNotValidERROR = "Email is not valid";
   public final String AddProductQuery = "INSERT INTO product(id, name, stock, price, currency, brand, category) VALUES (?,?,?,?,?,?,?)";
   public final String UpdateProductQuery = "UPDATE product SET name=?, stock=?, price=?, currency=?, brand=?, category=? WHERE id=";
   public final String DeleteProductQuery = "DELETE FROM product WHERE id=";
   public final String FetchAllProductQuery = "select * from product";
   public final String FetchProductByNameQuery = "select * from product WHERE name="; 
   public final String FetchProductByBrandQuery = "select * from product WHERE brand="; 
   public final String FetchProductByCategoryQuery = "select * from product WHERE category="; 
   public final String FetchAllAuthQuery = "select * from Auth";
   public final String DefaultRoleInsertQuery = "INSERT INTO userauthority(id, email, authority) VALUES(?,?,?)";
   public final String DefaultRole = "USER";
   public final String MerchantRole = "MERCHANT";
   public final String ExtractuserRolesQuery = "SELECT * FROM user_roles";
   public final String RoleExtractionQuery = "SELECT * FROM role";
   public final String UserVerifyQuery = "SELECT email, password , valid from auth WHERE email =?";
   public final String AuthorityVerifyQuery = "SELECT email, authority from userauthority WHERE email =?";
   public String roleQuery = "SELECT Customers.customer_id, Customers.first_name, Orders.amount"
                              +"FROM Customers"
		                      +"LEFT JOIN Orders"
		                      +"ON Customers.customer_id = Orders.customer";
   public final String verifyURL = "localhost:8080/shoppingApp/auth/";
   public String mailContent = "Dear [[name]],<br>"
           + "Please click the link below to verify your registration:<br>"
           + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
           + "Thank you,<br>"
           + "Shopping Application.";
   public final String Separator = "|";
   
}
