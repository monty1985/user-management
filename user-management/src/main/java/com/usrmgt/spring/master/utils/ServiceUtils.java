package com.usrmgt.spring.master.utils;

import java.util.logging.Logger;

import org.json.JSONObject;

import com.usrmgt.spring.master.domain.AtomicDeleteRequest;
import com.usrmgt.spring.master.domain.AtomicRegisterRequest;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServiceUtils {
	
	private final static Logger LOGGER = Logger.getLogger(ServiceUtils.class.getName());	
		
	public static Request buildRequestForRegisterUser(AtomicRegisterRequest req){ 
		
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");		
		String accessRights = req.getaccess();
		LOGGER.info("accessRights: "+ accessRights);
		ManageHost managerHost = new ManageHost(accessRights);
		String host = managerHost.getHost();
		String port = managerHost.getPort();		
		String appContext = managerHost.getContextPath();
		LOGGER.info("Context path for accessRights: [ "+ accessRights+" ] is [ "+appContext);
		String target = "/user/register";		
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://");
		urlBuilder.append(host);
		urlBuilder.append(":" + port);
		urlBuilder.append("/" + appContext);
		urlBuilder.append(target);		
		LOGGER.info("Url: body for the user: "+ req.getuserId()+ " "+urlBuilder.toString());
		
		//build request body
		JSONObject json = new JSONObject();
		json.accumulate("firstName", req.getfirstName());
		json.accumulate("lastName",  req.getlastName());
		json.accumulate("userId", req.getuserId());
		json.accumulate("email", req.getemail());
		json.accumulate("access", accessRights);
		
		String jsonString = json.toString();
		LOGGER.info("json body for the user: "+ req.getuserId()+ " "+jsonString);
		RequestBody body = RequestBody.create(JSON, jsonString);

		Request request = new Request.Builder()
				.url(urlBuilder.toString())
				.post(body)
				.build();

		return request;
	}
	
	public static Request buildRequestForDeleteUser(AtomicDeleteRequest req){	
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");		
		String accessRights = req.getaccess();
		
		LOGGER.info("accessRights: "+ accessRights);
		ManageHost managerHost = new ManageHost(accessRights);
		String host = managerHost.getHost();
		String port = managerHost.getPort();		
		String appContext = managerHost.getContextPath();
		
		LOGGER.info("Context path for accessRights: [ "+ accessRights+" ] is [ "+appContext);
		String target = "/user/delete";		
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://");
		urlBuilder.append(host);
		urlBuilder.append(":" + port);
		urlBuilder.append("/" + appContext);
		urlBuilder.append(target);
		
		LOGGER.info("del Url for the user: "+ req.getuserId()+ " "+urlBuilder.toString());		
		//build request body
		JSONObject json = new JSONObject();
		json.accumulate("userId", req.getuserId());		
		json.accumulate("access", accessRights);		
		String jsonString = json.toString();
		
		LOGGER.info("json body for the user: "+ req.getuserId()+ " "+jsonString);
		RequestBody body = RequestBody.create(JSON, jsonString);
		Request request = new Request.Builder()
				.url(urlBuilder.toString())
				.delete(body)
				.build();
		return request;		 
	}
}
