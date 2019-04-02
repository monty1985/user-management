package com.usrmgt.spring.master.domain;

public class UserResponse {
	private String status;
	private String userId;	
	private String body;
    private int httpResponseStatus;
    private int duration;
	
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
	
	public void setbody(String body) {
		this.body = body;
	}
	
	public String getbody() {
		if(body != null && body.trim().isEmpty()) {
            return null;
        }
		return body;
	}
	
	public void sethttpResponseStatus(int httpResponseStatus) {
		this.httpResponseStatus = httpResponseStatus;
	}
	
	public int gethttpResponseStatus() {
		return httpResponseStatus;
	}
	
	public void setduration(int duration) {
		this.duration = duration;
	}
	
	public int getduration() {
		return duration;
	}
	 
	
	
	
	
	
	

}
