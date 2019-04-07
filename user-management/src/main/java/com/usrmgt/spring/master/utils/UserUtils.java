package com.usrmgt.spring.master.utils;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.usrmgt.spring.master.domain.UserRegister;
import com.usrmgt.spring.master.domain.DeleteRequestContainer;
import com.usrmgt.spring.master.domain.RegisterRequestContainer;
import com.usrmgt.spring.master.domain.UserDelete;
import com.usrmgt.spring.master.domain.UserDeleteRequests;
import com.usrmgt.spring.master.domain.UserRequests;
import com.usrmgt.spring.node.dto.AtomicDeleteRequest;
import com.usrmgt.spring.node.dto.AtomicRegisterRequest;


public class UserUtils {
    
	private static final Logger LOGGER = Logger.getLogger(UserUtils.class.getName());
	
	/**
	 * This static method helps to deserialize the incoming JSON OBJECT to a simple user register request.
	 * 
	 * @return The List of Atomic Register request.
	 */
	
	public static List<AtomicRegisterRequest> getDeSerializedUserRegisterRequest(UserRequests request){		
		List<AtomicRegisterRequest> requestList =  new RegisterRequestContainer().getRequestList();
		for (UserRegister user : request.getUsers()) {
			LOGGER.info("User info :" + user);
			if(user==null) throw new RuntimeException("User info parsing exeception exception");
			String[] access = user.getaccess(); 			
			for(String ac : access) {
				if(user==null) throw new RuntimeException("User info access parsing exeception exception");
				 LOGGER.info("Access for the User : ["+user.getuserId() + "] "+ ac);
				AtomicRegisterRequest req = new AtomicRegisterRequest();				
			    req.setfirstName(user.getfirstName());
			    req.setlastName(user.getlastName());
			    req.setuserId(user.getuserId());
			    req.setemail(user.getemail());
			    req.setaccess(ac);
			    LOGGER.info("AtomicRegisterRequest :" + req);
			    requestList.add(req);
			}			
		}
		return requestList;
	}
	
	/**
	 * This static method helps to deserialize the incoming JSON OBJECT to a simple user delete request.
	 * 
	 * @return The List of Atomic Register request.
	 */
	
	public static List<AtomicDeleteRequest> getDeSerializedUserDeleteRequest(UserDeleteRequests delrequests){		
		List<AtomicDeleteRequest> delRequestList =  new DeleteRequestContainer().getRequestList();
		for (UserDelete user : delrequests.getUsers()) {
			LOGGER.info("delete user info :" + user);	
			if(user==null) throw new RuntimeException("User info parsing exeception exception");
			String[] access = user.getaccess(); 			
			for(String ac : access) {
				if(user==null) throw new RuntimeException("User info access parsing exeception exception");
				 LOGGER.info("Access for the User : ["+user.getuserId() + "] "+ ac);
				AtomicDeleteRequest req = new AtomicDeleteRequest();			
			    req.setuserId(user.getuserId());			   
			    req.setaccess(ac);
			    LOGGER.info("AtomicRegisterRequest :" + req);
			    delRequestList.add(req);
			}			
		}
		return delRequestList;
	}
	
	
	/**
	 * Gets the the file path to test resources directory.
	 * 
	 * @return The test directory file path.
	 */
	public static String getPathToTestResourcesDirectory() {
		String filepath = System.getProperty("user.dir") +  File.separator +"webapps"+ File.separator +"user-management" 
	     + File.separator + "WEB-INF" + File.separator+ "classes";
//				+  File.separator +"com"+  File.separator +"usrmgt"+ File.separator +"spring"+  File.separator +"master"+  File.separator +"resources";
		
		 LOGGER.info("File path: "+ filepath); 
		return filepath;
	}
	
}
