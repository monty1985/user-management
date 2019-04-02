package com.usrmgt.spring.master.domain;

import java.util.List;

public class AggregateUserResponse {
	
	private List<UserResponse> responses;
    private int duration;
    
    public AggregateUserResponse(List<UserResponse> responses, int duration) {
    	this.responses = responses;
    	this.duration = duration;
    }
    
    public AggregateUserResponse(){
    	
    }    
      
    public void setresponses(List<UserResponse> responses) {
    	this.responses = responses;
    }
    
    public List<UserResponse> getresponses() {
    	return responses;
    }
    
    public void setduration(int duration) {
    	this.duration = duration;
    }
    
    public int getduration() {
    	return duration;
    }

}
