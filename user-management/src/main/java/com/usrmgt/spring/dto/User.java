package com.usrmgt.spring.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
    @Size(max=30)
	private String firstName;
    
    @Size(max=30)
	private String lastName;
    
    @Size(min=5, max=15)
    @NotEmpty
    @Pattern(regexp="[A-z0-9_\\.\\-@\\s]+")
	private String userId;
	
	@Email	
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
