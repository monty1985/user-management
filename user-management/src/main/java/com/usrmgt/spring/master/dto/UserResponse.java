package com.usrmgt.spring.master.dto;

public class UserResponse {
	private String status;
	private String userId;
	
	public String getstatus() {
	   return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}
	
	public String getuserId() {
		return userId;
	}
	
	public void setuserid(String userId) {
		this.userId = userId ;
	}

}
