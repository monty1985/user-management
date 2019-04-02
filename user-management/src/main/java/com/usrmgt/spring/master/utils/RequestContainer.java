package com.usrmgt.spring.master.utils;

import java.util.ArrayList;
import java.util.List;

import com.usrmgt.spring.master.domain.AtomicRegisterRequest;

public class RequestContainer {  
	private List<AtomicRegisterRequest> requestList = new ArrayList<AtomicRegisterRequest>();

	public List<AtomicRegisterRequest> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<AtomicRegisterRequest> requestList) {
		this.requestList = requestList;
	}	
}
