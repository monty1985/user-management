package com.usrmgt.spring.master.domain;

import java.util.List;

public class UserRequests {
	
	private List<User> users;
	
	public List<User> getUsers(){
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
