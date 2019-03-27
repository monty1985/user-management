package com.usrmgt.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.usrmgt.spring.dto.User;
import com.usrmgt.spring.dto.UserResponse;
import com.usrmgt.spring.utils.CONSTANTS;

@Repository
public class UserDAO {
	
	public UserResponse registerUser(User user) throws NamingException, SQLException{
		int impactedRows = 0;	
		String updateStatus = "fail";
		UserResponse userReply = new UserResponse();
		String firstName = user.getfirstName();
		String lastName = user.getlastName();
		String email = user.getemail();
		String userid = user.getuserId();
		String accessType = user.getaccessType();	
		Connection conn = DBUtils.getConnection();			      
		try {
			String query = "INSERT INTO "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+"("+CONSTANTS.dbColfirstName+", "+CONSTANTS.dbColLastNameDB+", "+CONSTANTS.dbColEmailDB+", "
					+CONSTANTS.dbColUserID+", "+CONSTANTS.dbColAccess+") " + "values (?, ?, ?, ?, ?)";			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, firstName);
			preparedStmt.setString (2, lastName);
			preparedStmt.setString (3, email);
			preparedStmt.setString (4, userid);
			preparedStmt.setString (5, accessType);			
			impactedRows = preparedStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {			
			if (conn != null) conn.close();
			conn = null;
		}
		if( impactedRows > 0 ) {
			updateStatus = "succesful";
		}	
		userReply.setstatus(firstName+ "  "+lastName+ " registeration status: "+ updateStatus );
		userReply.setuserid(userid);		
		return userReply;
	}

	public UserResponse deleteUser(User user) throws SQLException, NamingException {
		String userid = user.getuserId();
		int impactedRows = 0;	
		UserResponse userReply = new UserResponse();
		String delStatus = "failed";		
		Connection conn = DBUtils.getConnection();
		try (Statement stm = conn.createStatement()) {
//			String delUserquery = "DELETE FROM "+ CONSTANTS.dbName +"."+ CONSTANTS.dbtableName + " WHERE "+CONSTANTS.dbColUserID+" = ?";			
//			PreparedStatement preparedStmt = conn.prepareStatement(delUserquery);
//			preparedStmt.setString (1, "'"+userid+"'");				
//			impactedRows = preparedStmt.executeUpdate(delUserquery);
			String delUserquery = "DELETE FROM Usermanagerdb.users WHERE userid = '" +userid+"'";			
			impactedRows = stm.executeUpdate(delUserquery);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {			
			if (conn != null) conn.close();
			conn = null;
		}
		if( impactedRows > 0 ) {
			delStatus = "succesful";
		}	
		userReply.setstatus("user id deletion status: "+ delStatus );
		userReply.setuserid(userid);	
		return userReply;
	}
}
