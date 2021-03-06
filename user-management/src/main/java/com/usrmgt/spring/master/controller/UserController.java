package com.usrmgt.spring.master.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.usrmgt.spring.master.domain.AggregateUserResponse;
import com.usrmgt.spring.master.domain.DeleteTask;
import com.usrmgt.spring.master.domain.Task;
import com.usrmgt.spring.master.domain.UserDeleteRequests;
import com.usrmgt.spring.master.domain.UserRequests;
import com.usrmgt.spring.master.service.UserService;
import com.usrmgt.spring.master.utils.UserUtils;
import com.usrmgt.spring.node.dao.UserDAO;
import com.usrmgt.spring.node.dto.AtomicDeleteRequest;
import com.usrmgt.spring.node.dto.AtomicRegisterRequest;
import com.usrmgt.spring.node.dto.UserResponse;

@RestController
public class UserController {

	private final UserService service;
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@Autowired
	public UserController(final UserService service ) {
		this.service = service;
	}

	@Autowired
	UserDAO userDao;

	@RequestMapping(value = "/register/users", method = RequestMethod.POST,consumes="application/json", produces = "application/json" )	
	public DeferredResult<ResponseEntity<AggregateUserResponse>> registerUsers(@RequestBody UserRequests regRequests) {
		DeferredResult<ResponseEntity<AggregateUserResponse>> result = new DeferredResult<>();
		UserResponse response = new UserResponse();		
		try {
		List<AtomicRegisterRequest> requestList = UserUtils.getDeSerializedUserRegisterRequest(regRequests);
		Task task = new Task(result, requestList);		
		service.executeRegister(task);
		} catch (Exception e) {
			LOGGER.info(e.toString());
			response.setstatus("FAILED: Exception could not process the input");
			result.setErrorResult(response);
		}
	    return result;	
	}

	@RequestMapping(value = "/delete/users", method = RequestMethod.DELETE,consumes="application/json", produces = "application/json" )	
	public DeferredResult<ResponseEntity<AggregateUserResponse>> deleteUsers(@RequestBody UserDeleteRequests delRequest) {
		DeferredResult<ResponseEntity<AggregateUserResponse>> delUserResult = new DeferredResult<>();	
		UserResponse delResponse = new UserResponse();		
		try {
		List<AtomicDeleteRequest> delRequestList = UserUtils.getDeSerializedUserDeleteRequest(delRequest);
		DeleteTask deltask = new DeleteTask(delUserResult, delRequestList);	
		service.executeDelete(deltask);
		} catch (Exception e) {
			LOGGER.info(e.toString());
			delResponse.setstatus("FAILED: Fatal Exception could not process the input request to delete user");
			delUserResult.setErrorResult(delResponse);
		}
		return delUserResult;		
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST,consumes="application/json", produces = "application/json" )	
	public UserResponse registerUser(@RequestBody AtomicRegisterRequest registerReq) throws NamingException, SQLException {
		UserResponse response = userDao.registerUser(registerReq);
		return response;
	}	

	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE, consumes="application/json", produces = "application/json")	
	public UserResponse deleteUser(@RequestBody AtomicDeleteRequest deleteReq) throws NamingException, SQLException {
		UserResponse response = userDao.deleteUser(deleteReq);
		return response;
	}
}
