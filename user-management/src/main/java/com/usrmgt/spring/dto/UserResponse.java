package com.usrmgt.spring.dto;

public class UserResponse {
	private String status;
	private String userID;
	
	public String getStatus() {
	   return status;
	}

	public void setStatus(String iStatus) {
		this.status = iStatus;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String iUser) {
		this.userID = iUser ;
	}

}
