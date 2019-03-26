package com.usrmgt.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.usrmgt.spring.dto.User;
import com.usrmgt.spring.dto.UserResponse;

@Repository
public class UserDAO {

	private static final String dbName = "usermanagerdb";
	private static final String dbtableName = "users";
	private static final String dbColfirstName = "first_name";
	private static final String dbColLastNameDB = "last_name";
	private static final String dbColEmailDB = "email_id";
	private static final String dbColUserID = "userid";
	private static final String dbColAccess = "access";

	public UserResponse registerUser(User user) throws NamingException, SQLException{
		int impactedRows = 0;	
		String updateStatus = "fail";
		UserResponse userReply = new UserResponse();
		String userid = user.getuserId();
		String firstName = user.getfirstName();
		String lastName = user.getlastName();
		String email = user.getemail();
		String accessType = "read";	
		Connection conn = DBUtils.getConnection();			      
		try (Statement stm = conn.createStatement()) {
			String registerUserquery = "INSERT INTO "+dbName+"."+dbtableName+"("+dbColfirstName+", "+dbColLastNameDB+", "+dbColEmailDB+", "+dbColUserID+", "+dbColAccess+") "
					+ "VALUES ('"+firstName+"','"+lastName+"', '"+email+"','"+userid+"','"+accessType+"');";			
			impactedRows = stm.executeUpdate(registerUserquery);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			// Release connection back to the pool
			if (conn != null) {
				conn.close();
			}
			conn = null; // prevent any future access
		}
		if( impactedRows > 0 ) {
			updateStatus = "succesful";		
		}	
		userReply.setstatus(firstName+ "  "+lastName+ " registeration status: "+ updateStatus );
		userReply.setuserid(userid);		
		return userReply;
	}

	public void deleteUster() {

	}	

	public void isUserExist() {

	}


}
