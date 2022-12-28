package com.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class Roles {
	
	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    
	    @Column(name = "email")
	    private String email;
	    
	    @Column(name = "role")
	    private String role;
	    
	    public Roles() {}

		public Roles(Integer id, String email, String role) {
			super();
			this.id = id;
			this.email = email;
			this.role = role;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return "Roles [id=" + id + ", email=" + email + ", role=" + role + "]";
		}
		
		
	    
	    
}
