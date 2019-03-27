package com.usrmgt.spring.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usrmgt.spring.dao.UserDAO;
import com.usrmgt.spring.dto.User;
import com.usrmgt.spring.dto.UserResponse;

@RestController
public class UserController {
		
	@Autowired
	UserDAO userDao;		
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)	
	public UserResponse registerUser(@RequestBody User user) throws NamingException, SQLException {
		UserResponse response = userDao.registerUser(user);
		return response;	
	}	
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)	
	public UserResponse deleteUser(@RequestBody User user) throws NamingException, SQLException {
		UserResponse response = userDao.deleteUser(user);
		return response;
	}
}
