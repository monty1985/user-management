package com.usrmgt.spring.master.domain;

import java.util.ArrayList;
import java.util.List;

import com.usrmgt.spring.node.dto.AtomicRegisterRequest;

public class RegisterRequestContainer {  
	private List<AtomicRegisterRequest> requestList = new ArrayList<AtomicRegisterRequest>();

	public List<AtomicRegisterRequest> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<AtomicRegisterRequest> requestList) {
		this.requestList = requestList;
	}	
}
