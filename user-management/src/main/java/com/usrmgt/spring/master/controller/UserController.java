package com.usrmgt.spring.master.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usrmgt.spring.master.dao.UserDAO;
import com.usrmgt.spring.master.dto.User;
import com.usrmgt.spring.master.dto.UserResponse;
import com.usrmgt.spring.master.dto.UserWrapper;

@RestController
public class UserController {

	@Autowired
	UserDAO userDao;		

	@RequestMapping(value = "/user/register", method = RequestMethod.POST,consumes="application/json", produces = "application/json" )	
	public UserResponse registerUser(@RequestBody User user) throws NamingException, SQLException {
		UserResponse response = userDao.registerUser(user);
		return response;	
	}	

	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)	
	public UserResponse deleteUser(@RequestBody User user) throws NamingException, SQLException {
		UserResponse response = userDao.deleteUser(user);
		return response;
	}

	@RequestMapping(value = "/users/register", method = RequestMethod.POST,consumes="application/json", produces = "application/json" )	
	public List<String> registerUsers(@RequestBody UserWrapper usersWrapper) {
		List<String> response = new ArrayList<String>();
		for (User user: usersWrapper.getUsers()){      
			response.add("Saved users: " + user.toString());
		}
		return response;
				
		
		/*
		 * Receive the Jason AArray, JASON Array consists of list of users and each user with the list of access rights
		 * and iterate the list of Jason Objects and get each User.
		 * Get the rights of the user and store it in an array. 
		 * Determine the rest API component (s) to call based on the rights of the user. 
		 * Case 1) if the user rights contains rights that are for 2 different database schema. 2 Rest Calls should be made. 
		 * Case 2) if the user rights contains rights that are for same database schema. just 1 rest call should be made. 
		 * 
		 * Rest Call set up: 
		 *   1) Get the rest IP address, Port, context path , and resource target. 
		 *   2) Prepare a rest client for each access ( create Url , create post request )
		 *   3) Construct a request Logic( Based on the rights )
		 *   4) Send request to the node.
		 *   
		 * Rest Request-Response container (Class) - The request container should have the list of Rest request Objects and its response.    
		 *      
		 * Rest Request Object (Class)- This is a request object that we create with all the required information for the Rest Request O
		 * 
		 * Rest Response Container (Class) - The request container should have the list of Rest request Objects.
	
		 * Construct a request back to the main request with the userID creation status. 
		 * 
		 * 
		 */
		
	}
}
