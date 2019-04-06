package com.usrmgt.spring.master.domain;

public class Host {
	
	private String hostname;
	private String port;
	private String context;
	
	public Host(String hostname, String port, String context) {
		this.hostname = hostname;
		this.port     = port;
		this.context  = context;		
	}
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
