package com.testproblem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This Data access object is used to connect to mysql database 
 * Specify the proper DB_URL,User name and password
 */

public class Dao_Mysql {

	private static Connection connection = null;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/testspring";//Specify the Database URL

	  //  Database credentials
	  static final String USER = "root";
	  static final String PASS = "";
	  
	  
	  public static Connection getConnection(){
		  
		  if (connection != null)
				return connection;
			else {
				try{
					   
					  System.out.println("Connecting to Database...");
				      //Register JDBC driver
				      Class.forName("com.mysql.jdbc.Driver");

				      // Open a connection
				      System.out.println("Connected to database...");
				      connection = DriverManager.getConnection(DB_URL,USER,PASS);
				   }catch(SQLException se){
					      //Handling errors for JDBC
					   		System.out.println("SQL Exception : "+se.toString());
					   		se.printStackTrace();
					   }catch(Exception e){
					      //Handling errors for Class.forName
						   System.out.println("Exception : "+e.toString());
					   } 
				return connection;
			}
		  
	  }
	
}
