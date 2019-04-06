package com.usrmgt.spring.node.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.usrmgt.spring.master.utils.CONSTANTS;
import com.usrmgt.spring.node.dto.AtomicDeleteRequest;
import com.usrmgt.spring.node.dto.AtomicRegisterRequest;
import com.usrmgt.spring.node.dto.UserResponse;

@Repository
public class UserDAO {

	private final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());	

	public UserResponse registerUser(AtomicRegisterRequest regRequest) throws NamingException, SQLException{		
		int impactedRows = -1;	
		String updateStatus = "fail or some sql error ";
		UserResponse userReply = new UserResponse();
		String firstName  = regRequest.getfirstName();
		String lastName   = regRequest.getlastName();
		String email      = regRequest.getemail();
		String userid     = regRequest.getuserId();
		String accessType = regRequest.getaccess();
		LOGGER.info("first_name: "+firstName+", last_name: "+lastName+", email_id: "+ email + "userid, " +userid);
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

			String userExistWithBothAccessQuery = "SELECT * FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ? AND "+CONSTANTS.dbColR3+" = ? AND "+CONSTANTS.dbColR4+" =?";
			LOGGER.info("user Exist With Both Access Query :" + userExistWithBothAccessQuery);
			PreparedStatement preparedStmt2 = conn.prepareStatement(userExistWithBothAccessQuery);
			preparedStmt2.setString (1, userid);
			preparedStmt2.setString (2, "Y");	
			preparedStmt2.setString (3, "Y");
			if( preparedStmt2.executeQuery().next()){
				updateStatus = "Fail- User exist with the requested Access";
			} else {				
				String query = "INSERT INTO "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+"("+CONSTANTS.dbColfirstName+", "+CONSTANTS.dbColLastNameDB+", "+CONSTANTS.dbColEmailDB+", "
						+CONSTANTS.dbColUserID+", "+ dbAccessCol+") " + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "+dbAccessCol+" = VALUES("+dbAccessCol+")" ;
				LOGGER.info("Insert user query :" + query);
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString (1, firstName);
				preparedStmt.setString (2, lastName);
				preparedStmt.setString (3, email);
				preparedStmt.setString (4, userid);
				preparedStmt.setString (5, "Y");			
				impactedRows = preparedStmt.executeUpdate();				
				if ( impactedRows == 1 ) {
					updateStatus = "Success- User created with the requested Access";
				} else if(impactedRows == 2 ) {
					updateStatus = "Success- User updated with the requested Access";
				} 				
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {			
			if (conn != null) conn.close();
			conn = null;
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
			LOGGER.info("check If User Exist Query :" + checkIfUserExistQuery);
			PreparedStatement preparedStmt1 = conn.prepareStatement(checkIfUserExistQuery);
			preparedStmt1.setString (1, userid);		
			if (preparedStmt1.execute()){			
				String userExistWithBothAccessQuery = "SELECT * FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ? AND "+CONSTANTS.dbColR3+" = ? AND "+CONSTANTS.dbColR4+" =?";
				LOGGER.info("user Exist With Both Access Query :" + userExistWithBothAccessQuery);

				PreparedStatement preparedStmt2 = conn.prepareStatement(userExistWithBothAccessQuery);
				preparedStmt2.setString (1, userid);
				preparedStmt2.setString (2, "Y");	
				preparedStmt2.setString (3, "Y");
				if( preparedStmt2.executeQuery().next()){
					LOGGER.info("user Exist With Both Access Query So updating the access");
					String dropAccessQurry= " Update "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" SET "+dbAccessCol+" = ? WHERE "+CONSTANTS.dbColUserID+" = ? and "+dbAccessCol+" = ?";
					LOGGER.info("dropAccessQurry"+ dropAccessQurry);
					PreparedStatement preparedStmt3 = conn.prepareStatement(dropAccessQurry);
					preparedStmt3.setString (1, "N");
					preparedStmt3.setString (2, userid);	
					preparedStmt3.setString (3, "Y");
					impactedRows = preparedStmt3.executeUpdate();
					if(impactedRows > 0) updateStatus = "Successful: User was removed access and not deleted ";

				} else{
					String dropRecordQuery= "DELETE FROM "+ CONSTANTS.dbName+"."+CONSTANTS.dbtableName+" WHERE "+CONSTANTS.dbColUserID+" = ?";
					LOGGER.info("dropRecordQuery"+ dropRecordQuery);
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