package com.usrmgt.spring.master.domain;

import java.util.List;

public class AggregateUserResponse {
	
	private List<HTTPResponseCollector> responses;
    private int duration;
    
    public AggregateUserResponse(List<HTTPResponseCollector> responses, int duration) {
    	this.responses = responses;
    	this.duration = duration;
    }
    
    public AggregateUserResponse(){
    	
    }    
      
    public void setresponses(List<HTTPResponseCollector> responses) {
    	this.responses = responses;
    }
    
    public List<HTTPResponseCollector> getresponses() {
    	return responses;
    }
    
    public void setduration(int duration) {
    	this.duration = duration;
    }
    
    public int getduration() {
    	return duration;
    }

}
