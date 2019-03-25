package com.usrmgt.spring.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.usrmgt.spring.dto.User;
import com.usrmgt.sprint.dao.DBUtils;
import com.usrmgt.sprint.dao.UserDAO;

@Controller
public class UserController {
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws NamingException 
	 * @throws SQLException 
	 * 
	 * 
	 */
	
	@Autowired
	UserDAO userDao;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws NamingException, SQLException {
		
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		
        Connection conn = DBUtils.getConnection();
        StringBuilder msg = new StringBuilder();
		
        /**
         * Use Connection to query the database for a simple table listing.
         * Statement will be closed automatically.
         */
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

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(@Validated User user, Model model) {
		System.out.println("User Page Requested");
		model.addAttribute("userName", user.getUserName());
		return "user";
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String registerUser(@Validated User user, Model model) throws NamingException, SQLException {
		// validate User details Format and other info
		// get connection
		// Check if UserID exist. if exist return userID exist exception
		// Check if the user exist with the first name and the last name if the user name exist. return the User Exist Exception. 
		// Update the table with the new user and send the successful message. 
		// 
		// Connection conn = DBConnection.getConnection();	
		
		return "user";
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String deleteUser(@Validated User user, Model model) throws NamingException, SQLException {
		 // get connection
		// Check if UserID exist. Delete the user data in the data base send the successmessage
		// if the User ID dont exist. return User dont exist exception
		// Connection conn = DBConnection.getConnection();		
		return "user";
	}
	
	
	
	
	
	
	
	
	
	
	
}
