package com.usrmgt.spring.dao;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtils {
	
	private static String dataResourceName = "jdbc/usermanagerdb";	
	
	public String getDataSourceName() {
		return dataResourceName;
	}

	public static Connection getConnection() throws SQLException, NamingException {
		Context initialContext = new InitialContext();	
		Context environmentContext = (Context) initialContext.lookup("java:comp/env");
		DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
		return dataSource.getConnection();
	}
	

}
