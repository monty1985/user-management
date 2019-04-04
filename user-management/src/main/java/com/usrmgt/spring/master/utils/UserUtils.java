package com.usrmgt.spring.master.utils;

import java.util.List;
import java.util.logging.Logger;

import com.usrmgt.spring.master.domain.AtomicDeleteRequest;
import com.usrmgt.spring.master.domain.AtomicRegisterRequest;
import com.usrmgt.spring.master.domain.User;
import com.usrmgt.spring.master.domain.UserDelete;
import com.usrmgt.spring.master.domain.UserDeleteRequests;
import com.usrmgt.spring.master.domain.UserRequests;


public class UserUtils {
    
	private static final Logger LOGGER = Logger.getLogger(UserUtils.class.getName());
	
	public static List<AtomicRegisterRequest> getDeSerializedUserRegisterRequest(UserRequests request){		
		List<AtomicRegisterRequest> requestList =  new RegisterRequestContainer().getRequestList();
		for (User user : request.getUsers()) {
			LOGGER.info("User info :" + user);		
			String[] access = user.getaccess(); 			
			for(String ac : access) {
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
	
	public static List<AtomicDeleteRequest> getDeSerializedUserDeleteRequest(UserDeleteRequests delrequests){		
		List<AtomicDeleteRequest> delRequestList =  new DeleteRequestContainer().getRequestList();
		for (UserDelete user : delrequests.getUsers()) {
			LOGGER.info("delete user info :" + user);		
			String[] access = user.getaccess(); 			
			for(String ac : access) {
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
	
	
	
	
}
