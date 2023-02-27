package com.shopping.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
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
	
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}


	public boolean isValid() {
		return isValid;
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
	
}
