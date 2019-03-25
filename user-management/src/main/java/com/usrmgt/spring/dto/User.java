package com.usrmgt.spring.dto;

public class User {
	
	private String firstname;
	private String lastname;
	private String user_id;
	private String email;

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getuserID() {
		return user_id;
	}
	
	public void setUserID(String userId) {
		this.user_id = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
