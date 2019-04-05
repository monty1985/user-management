package com.usrmgt.spring.master.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.usrmgt.spring.master.exceptions.PropertyNotFound;
import com.usrmgt.spring.master.utils.PropertiesHelper.HostProperties;

public class ManageHostImpl implements ManageHost{
    
	private static Map<String,List<Host>> hostAccessMap;
	private HostProperties properties;
	private String hostPropertyName = "ManageHost";
	
	void ManagerHost() throws FileNotFoundException, Exception {
		init();		
	}	
	
	public static Map<String,List<Host>> gethostAccessMap(){
		return hostAccessMap;
	}

	public  void init() throws FileNotFoundException, Exception{		
		properties = PropertiesHelper.loadTestProperties(hostPropertyName);	
		hostAccessMap = generateHostAccessMap();
	}
	
	public List<String> getAllNode() {
		return PropertiesHelper.getComponentsFromProperties(properties);		
	}
	
	public List<String> getNodesByPurpose(String purpose) {
		return PropertiesHelper.getHostFromPropertiesByPurpose(properties, purpose);
	}
	
	public List<String> getNodesByAccess(String access) {
		return PropertiesHelper.getHostFromPropertiesByAccess(properties, access);		
	}
	
	public List<Host> getHostListByAccess(String access) throws PropertyNotFound {
		List<Host> hostList = new ArrayList<Host>();
		   List<String> nodes = getNodesByAccess(access);
		   for(String node: nodes) {
			   String hostname = properties.getRequiredProperty(node + ".hostname");
			   String port = properties.getRequiredProperty(node + ".port");
			   String context = properties.getRequiredProperty(node + ".context");
			   hostList.add(new Host(hostname,port,context));
		   }
		return hostList;
	}
	
	
	public  Map<String,List<Host>> generateHostAccessMap() throws PropertyNotFound{
		Map<String,List<Host>> hostAccessMap = new HashMap<String,List<Host>>();
		List<String> accessList = PropertiesHelper.getAccessList(properties);
		for(String access: accessList) {
			List<Host> hostList = getHostListByAccess(access);
			hostAccessMap.put(access, hostList);
		}		
		return hostAccessMap;
	}
	
	
	

//	
//	
//	
//	
//
//	private String accessRights;
//
//	ManageHostImpl(String accessRights){
//		this.accessRights = accessRights.trim();
//	}
//	
//	
//	
//
//	public String getHost() {
//		String host = "localhost";
//		switch (accessRights) {
//		case "R1":
//			host = "localhost";
//		case "R2":
//			host = "localhost";
//		case "R3":
//			host = "localhost";
//		case "R4":
//			host = "localhost";
//		case "R5":
//			host = "localhost";
//		case "R6":
//			host = "localhost";
//		}
//		return host;
//	}
//
//	public String getPort() {
//		String port = "8080";
//		switch (accessRights) {
//		case "R1":
//			port = "8080";
//		case "R2":
//			port = "8080";
//		case "R3":
//			port = "8080";
//		case "R4":
//			port = "8080";
//		case "R5":
//			port = "8080";
//		case "R6":
//			port = "8080";
//		}
//		return port;
//	}
//
//	public String getContextPath() {
//		String contextPath = "user-management-node1";
//		if(accessRights.equalsIgnoreCase("R1") || accessRights.equalsIgnoreCase("R2"))
//			contextPath = "user-management-node1";
//		if(accessRights.equalsIgnoreCase("R3") || accessRights.equalsIgnoreCase("R4"))
//			contextPath = "user-management";
//		if(accessRights.equalsIgnoreCase("R5") || accessRights.equalsIgnoreCase("R6")) 
//			contextPath = "user-management-node2";		
//		return contextPath;
//	}
//	
}
