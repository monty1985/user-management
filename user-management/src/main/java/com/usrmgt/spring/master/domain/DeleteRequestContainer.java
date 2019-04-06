package com.usrmgt.spring.master.domain;

import java.util.ArrayList;
import java.util.List;

import com.usrmgt.spring.node.dto.AtomicDeleteRequest;

public class DeleteRequestContainer {  
	private List<AtomicDeleteRequest> delRequestList = new ArrayList<AtomicDeleteRequest>();

	public List<AtomicDeleteRequest> getRequestList() {
		return delRequestList;
	}

	public void setRequestList(List<AtomicDeleteRequest> delRequestList) {
		this.delRequestList = delRequestList;
	}	
}
