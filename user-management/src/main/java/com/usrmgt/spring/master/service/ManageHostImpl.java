package com.usrmgt.spring.master.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.usrmgt.spring.master.domain.Host;
import com.usrmgt.spring.master.exceptions.PropertyNotFound;
import com.usrmgt.spring.master.utils.PropertiesHelper;
import com.usrmgt.spring.master.utils.PropertiesHelper.HostProperties;

public class ManageHostImpl {
	
	private static final Logger LOGGER = Logger.getLogger(ManageHostImpl.class.getName());
    	
	private HostProperties properties;
	private String hostPropertyName = "ManageHost";
		
	protected ManageHostImpl() throws FileNotFoundException, Exception{
		properties = PropertiesHelper.loadTestProperties(hostPropertyName);
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
	
	
	public  Map<String,List<Host>> generateHostAccessMap() throws FileNotFoundException, Exception{			
		PropertiesHelper.printProperties(properties);
		if(properties==null) {
			LOGGER.info(" Property Failed to load for the path");
			throw new RuntimeException(" The Manager Host failed to load the available Nodes.");
		}
		Map<String,List<Host>> hostAccessMap = new HashMap<String,List<Host>>();
		List<String> accessList = PropertiesHelper.getAccessList(properties);
		LOGGER.info("ACCESS LIST: " + accessList);
		for(String access: accessList) {
			List<Host> hostList = getHostListByAccess(access);
			hostAccessMap.put(access, hostList);
		}		
		return hostAccessMap;
	}	
}
