package com.usrmgt.spring.master.dto;

import java.util.List;

public class UserWrapper {
	
	private List<User> users;
	
	public List<User> getUsers(){
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
