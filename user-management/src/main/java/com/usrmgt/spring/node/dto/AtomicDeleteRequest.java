package com.usrmgt.spring.node.dto;

public class AtomicDeleteRequest {

	private String userId;	
	private String access;
	
	public String getuserId() {
		return userId;
	}
	
	public void setuserId(String userId) {
		this.userId = userId;
	}	
	
	public String getaccess() {
		return access;
	}
	
	public void setaccess(String access) {
		this.access = access;
	}
	
	@Override
    public String toString() {
        return "User [id=" + userId + ", access=" + access + "]";
    }
}
