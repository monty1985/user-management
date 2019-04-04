package com.usrmgt.spring.master.domain;

import java.util.List;

public class UserDeleteRequests {
	
	private List<UserDelete> deleteUsers;
	
	public List<UserDelete> getUsers(){
		return deleteUsers;
	}
	
	public void setUsers(List<UserDelete> deleteUsers) {
		this.deleteUsers = deleteUsers;
	}	
}
