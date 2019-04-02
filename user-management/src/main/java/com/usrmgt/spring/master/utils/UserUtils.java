package com.usrmgt.spring.master.utils;

import java.util.List;
import java.util.logging.Logger;

import com.usrmgt.spring.master.domain.AtomicRegisterRequest;
import com.usrmgt.spring.master.domain.User;
import com.usrmgt.spring.master.domain.UserRequest;


public class UserUtils {
    
	private static final Logger LOGGER = Logger.getLogger(UserUtils.class.getName());
	public static List<AtomicRegisterRequest> getDeSerializedUserRegisterRequest(UserRequest request){		
		List<AtomicRegisterRequest> requestList =  new RequestContainer().getRequestList();
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
}
