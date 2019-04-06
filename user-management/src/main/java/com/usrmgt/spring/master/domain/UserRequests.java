package com.usrmgt.spring.master.domain;

import java.util.List;

public class UserRequests {
	
	private List<UserRegister> users;
	
	public List<UserRegister> getUsers(){
		return users;
	}
	
	public void setUsers(List<UserRegister> users) {
		this.users = users;
	}
	
}
