package com.usrmgt.spring.master.utils;

import java.util.ArrayList;
import java.util.List;

import com.usrmgt.spring.master.domain.AtomicDeleteRequest;

public class DeleteRequestContainer {  
	private List<AtomicDeleteRequest> delRequestList = new ArrayList<AtomicDeleteRequest>();

	public List<AtomicDeleteRequest> getRequestList() {
		return delRequestList;
	}

	public void setRequestList(List<AtomicDeleteRequest> delRequestList) {
		this.delRequestList = delRequestList;
	}	
}
