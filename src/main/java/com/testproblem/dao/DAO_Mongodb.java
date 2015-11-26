package com.testproblem.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * This Data access object is used to connect to mongodb database 
 * Specify the proper collection name 
 */

public class DAO_Mongodb {


	public static DB getConnection(){
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db=mongoClient.getDB("testspring");
		
		return db;
	}

	
}
