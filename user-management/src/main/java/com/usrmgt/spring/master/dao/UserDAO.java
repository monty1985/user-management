package com.usrmgt.spring.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.usrmgt.spring.master.domain.AtomicDeleteRequest;
import com.usrmgt.spring.master.domain.AtomicRegisterRequest;
import com.usrmgt.spring.master.domain.UserResponse;
import com.usrmgt.spring.master.utils.CONSTANTS;

@Repository
public class UserDAO {

	public UserResponse registerUser(AtomicRegisterRequest regRequest) throws NamingException, SQLException{		
		int impactedRows = 0;	
		String updateStatus = "fail";
		UserResponse userReply = new UserResponse();
		String firstName  = regRequest.getfirstName();
		String lastName   = regRequest.getlastName();
		String email      = regRequest.getemail();
		String userid     = regRequest.getuserId();
		String accessType = regRequest.getaccess();
		String dbAccessCol= "";
		if (accessType.equals("R3")) 
			dbAccessCol = CONSTANTS.dbColR3; 
		else if(accessType.equals("R4")) {
			dbAccessCol = CONSTANTS.dbColR4;			
		} else {
			throw new RuntimeException("Invalid access rights");
		}		
		Connection conn = DBUtils.getConnection();

		try {
			String query = "INSERT INTO "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+"("+CONSTANTS.dbColfirstName+", "+CONSTANTS.dbColLastNameDB+", "+CONSTANTS.dbColEmailDB+", "
					+CONSTANTS.dbColUserID+", "+ dbAccessCol+") " + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "+dbAccessCol+" = VALUES("+dbAccessCol+")" ;			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, firstName);
			preparedStmt.setString (2, lastName);
			preparedStmt.setString (3, email);
			preparedStmt.setString (4, userid);
			preparedStmt.setString (5, "Y");			
			impactedRows = preparedStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {			
			if (conn != null) conn.close();
			conn = null;
		}

		if( impactedRows == 0 ) {
			updateStatus = "User exist with the requested Access";
		} else if ( impactedRows == 1 ) {
			updateStatus = "User created with the requested Access";
		} else if(impactedRows == 2 ) {
			updateStatus = "User updated with the requested Access";
		}

		userReply.setstatus(userid +" registration status: "+ updateStatus );				
		return userReply;
	}

	public UserResponse deleteUser(AtomicDeleteRequest delRequest) throws SQLException, NamingException {		
		int impactedRows = -1;	
		String updateStatus = "fail";
		UserResponse userReply = new UserResponse();	
		String userid = delRequest.getuserId();
		String accessType = delRequest.getaccess();
		String dbAccessCol = "";
		if (accessType.equals("R3")) 
			dbAccessCol = CONSTANTS.dbColR3; 
		else if(accessType.equals("R4")) {
			dbAccessCol = CONSTANTS.dbColR4;			
		} else {
			throw new RuntimeException("Invalid access rights");
		}		
		Connection conn = DBUtils.getConnection();

		try {
			String checkIfUserExistQuery = "SELECT * FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ?";
			PreparedStatement preparedStmt1 = conn.prepareStatement(checkIfUserExistQuery);
			preparedStmt1.setString (1, userid);		
			if (preparedStmt1.executeUpdate( ) > 0){
				
				String userExistWithBothAccessQuery = "SELECT * FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ? AND "+CONSTANTS.dbColR3+" = ? AND "+CONSTANTS.dbColR4+" =?";
				PreparedStatement preparedStmt2 = conn.prepareStatement(userExistWithBothAccessQuery);
				preparedStmt2.setString (1, userid);
				preparedStmt2.setString (2, "Y");	
				preparedStmt2.setString (3, "Y");
				if( preparedStmt2.executeUpdate() > 0){

					String dropAccessQurry= " Update "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" SET "+dbAccessCol+" = ? WHERE "+CONSTANTS.dbColUserID+" = ? and "+dbAccessCol+" = ?";			   
					PreparedStatement preparedStmt3 = conn.prepareStatement(dropAccessQurry);
					preparedStmt3.setString (1, "N");
					preparedStmt3.setString (2, userid);	
					preparedStmt3.setString (3, "Y");
					impactedRows = preparedStmt3.executeUpdate();
					if(impactedRows > 0) updateStatus = "Successful: User was removed access and not deleted ";
				} else{

					String dropRecordQuery= "DELETE FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ?";				
					PreparedStatement preparedStmt4 = conn.prepareStatement(dropRecordQuery);
					preparedStmt4.setString (1, userid);	
					impactedRows = preparedStmt4.executeUpdate();
					if(impactedRows > 0) updateStatus = "Successful: User was deleted from table user has no other access ";

				}
			} else {
				updateStatus = "Failed: No user found in the database";
			}


		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {			
			if (conn != null) conn.close();
			conn = null;
		}
		
		userReply.setstatus(userid +" Deletion status: "+ updateStatus );		
		return userReply;
	}
}