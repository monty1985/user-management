package com.usrmgt.spring.master.dto;

public class User {

	private String firstName;
    
	private String lastName;

	private String userId;
	

	private String email;
	
	
	private String accessType;

	public String getfirstName() {
		return firstName;
	}
	
	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getlastName() {
		return lastName;
	}
	
	public void setlastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getuserId() {
		return userId;
	}
	
	public void setuserId(String userId) {
		this.userId = userId;
	}
	
	public String getemail() {
		return email;
	}
	
	public void setemail(String email) {
		this.email = email;
	}
	
	public String getaccessType() {
		return accessType;
	}
	
	public void setaccessType(String accessType) {
		this.accessType = accessType;
	}
	
	@Override
    public String toString() {
        return "User [id=" + userId + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
