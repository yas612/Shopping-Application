package com.shopping.entity;

import java.util.HashSet;
import java.util.Set;

import java.util.*;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Auth")
public class Auth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
    
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	@Email(message = "Email is not valid",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
	@Column(name="code", length = 64)
	private String verification_code;
	
	@Column(name="valid")
	private boolean isValid;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Roles> roles = new HashSet<>();
	

	public Auth() {
		super();
	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getVerification_code() {
		return verification_code;
	}


	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}


	public boolean isValid() {
		return isValid;
	}


	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	


	public Set<Roles> getRoles() {
		return roles;
	}


	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}


	public Auth(int id, String firstName, String lastName, String password, String email, String verification_code,
			boolean isValid) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.verification_code = verification_code;
		this.isValid = isValid;
	}
	
	
	


	public Auth(int id, String firstName, String lastName, String password,
			@Email(message = "Email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") @NotEmpty(message = "Email cannot be empty") String email,
			String verification_code, boolean isValid, Set<Roles> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.verification_code = verification_code;
		this.isValid = isValid;
		this.roles = roles;
	}


	public Auth(String password,
			@Email(message = "Email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") @NotEmpty(message = "Email cannot be empty") String email) {
		super();
		this.password = password;
		this.email = email;
	}


	@Override
	public String toString() {
		return "Auth [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", verification_code=" + verification_code + ", isValid=" + isValid + "]";
	}


	
	
	
	
}
