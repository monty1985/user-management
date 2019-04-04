package com.usrmgt.spring.master.domain;

public class HTTPResponseCollector {
	private String body;
    private int httpResponseStatus;
    private int duration;
    private String target;

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
	
	public void settarget(String target) {
		this.target = target;
	}
	
	public String gettarget() {
		return target;
	}
	
}
