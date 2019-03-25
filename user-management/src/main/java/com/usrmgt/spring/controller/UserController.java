package com.usrmgt.spring.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.usrmgt.spring.dao.DBUtils;
import com.usrmgt.spring.dao.UserDAO;
import com.usrmgt.spring.dto.User;
import com.usrmgt.spring.dto.UserResponse;

@Controller
public class UserController {
		
	@Autowired
	UserDAO userDao;
	
	@ResponseBody
	@RequestMapping(value = "/test/connection", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws NamingException, SQLException {		
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);		
        Connection conn = DBUtils.getConnection();
        StringBuilder msg = new StringBuilder();	
        try (Statement stm = conn.createStatement()) {
            String query = "show tables;";
            ResultSet rs = stm.executeQuery(query);
            // Store and return result of the query
            while (rs.next()) {
                msg.append(rs.getString("Tables_in_usermanagerdb"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            // Release connection back to the pool
            if (conn != null) {
                conn.close();
            }
            conn = null; // prevent any future access
        }
        model.addAttribute("msgArgument",
                "Maven Java Web Application Project: Success! The show tables result is: "
                        + msg.toString());
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public UserResponse registerUser(@RequestBody User user) throws NamingException, SQLException {
		UserResponse response = userDao.registerUser(user);
		return response;	
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public UserResponse deleteUser(@Validated User user, Model model) throws NamingException, SQLException {
		UserResponse response = userDao.registerUser(user);
		return response;
	}
}
