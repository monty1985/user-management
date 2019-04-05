package com.usrmgt.spring.master.utils;

import java.util.List;
import java.util.Map;

import com.usrmgt.spring.master.exceptions.PropertyNotFound;

public interface ManageHost {
	public List<String> getAllNode();
	public Map<String,List<Host>> generateHostAccessMap() throws PropertyNotFound;
    public List<String> getNodesByPurpose(String purpose);
    public List<Host> getHostListByAccess(String access) throws PropertyNotFound;
   }
