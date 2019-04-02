package com.usrmgt.spring.master.utils;

public class ManageHost {
	/*
	 * Host varies based on the user rights. 
	 * 
	 * 
	 */

	private String accessRights;

	ManageHost(String accessRights){
		this.accessRights = accessRights.trim();
	}

	public String getHost() {
		String host = "localhost";
		switch (accessRights) {
		case "R1":
			host = "localhost";
		case "R2":
			host = "localhost";
		case "R3":
			host = "localhost";
		case "R4":
			host = "localhost";
		case "R5":
			host = "localhost";
		case "R6":
			host = "localhost";
		}
		return host;
	}



	public String getPort() {
		String port = "8080";
		switch (accessRights) {
		case "R1":
			port = "8080";
		case "R2":
			port = "8080";
		case "R3":
			port = "8080";
		case "R4":
			port = "8080";
		case "R5":
			port = "8080";
		case "R6":
			port = "8080";
		}
		return port;
	}

	public String getWebservicePath() {		
		String webServicePath = "/user/register";
		switch (accessRights) {
		case "R1":
			webServicePath = "/user/register";
		case "R2":
			webServicePath = "/user/register";
		case "R3":
			webServicePath = "/user/register";
		case "R4":
			webServicePath = "/user/register";
		case "R5":
			webServicePath = "/user/register";
		case "R6":
			webServicePath = "/user/register";
		}
		return webServicePath;
	}

	public String getContextPath() {
		String contextPath = "user-management-node1";
		if(accessRights.equalsIgnoreCase("R1") || accessRights.equalsIgnoreCase("R2"))
			contextPath = "user-management-node1";
		if(accessRights.equalsIgnoreCase("R3") || accessRights.equalsIgnoreCase("R4"))
			contextPath = "user-management";
		if(accessRights.equalsIgnoreCase("R5") || accessRights.equalsIgnoreCase("R6")) 
			contextPath = "user-management-node2";		
		return contextPath;
	}
}
