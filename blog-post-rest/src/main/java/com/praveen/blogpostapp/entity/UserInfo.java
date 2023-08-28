package com.praveen.blogpostapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class UserInfo{
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	public UserInfo() {
		
	}
	public UserInfo(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
	private int id;
	
    @Column(name="name")
	private String name;
	
    @Column(name="email")
	private String email;
	
    @Column(name="password")
	private String password;

	private String role;
	public String getRoles(){
		return role;
	}
	public void setRoles(String user) {
        role=user;
	}
}
