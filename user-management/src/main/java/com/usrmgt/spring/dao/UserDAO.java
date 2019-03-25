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

	private static final String dbName = "USERmanagerdb";
	private static final String dbtableName = "users";
	private static final String dbColfirstName = "first_name";
	private static final String dbColLastNameDB = "last_name";
	private static final String dbColEmailDB = "email_id";
	private static final String dbColUserID = "userid";
	private static final String dbColAccess = "access";

	public UserResponse registerUser(User user) throws NamingException, SQLException{
		UserResponse userReply = new UserResponse();
		String userid = user.getuserID();
		String firstName = user.getFirstname();
		String lastName = user.getLastname();
		String email = user.getEmail();
		String accessType = "read";	
		Connection conn = DBUtils.getConnection();			      
		try (Statement stm = conn.createStatement()) {
			String registerUserquery = "INSERT INTO "+dbName+"."+dbtableName+"("+dbColfirstName+", "+dbColLastNameDB+", "+dbColEmailDB+", "+dbColUserID+", "+dbColAccess+") "
					+ "VALUES ('"+firstName+"','"+lastName+"', '"+email+"','"+userid+"','"+accessType+"');";
//			ResultSet rs = stm.executeQuery(registerUserquery);	 
			stm.executeQuery(registerUserquery);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			// Release connection back to the pool
			if (conn != null) {
				conn.close();
			}
			conn = null; // prevent any future access
		}
	
		userReply.setStatus(firstName+ "  "+lastName+ " is registered successfully to the users table");
		userReply.setUserID(userid);
		
		return userReply;
	}

	public void deleteUster() {

	}	

	public void isUserExist() {

	}


}
