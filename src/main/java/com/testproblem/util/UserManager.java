package com.testproblem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.testproblem.dao.DAO_Mongodb;
import com.testproblem.dao.Dao_Mysql;
import com.testproblem.model.User;

/**
 * This utility object is used for performing operations on User model. 
 * After invoking the object, database attribute needs to be specified. 
 *
 * Example usage:
 * UserManager usm=new UserManager(); 
 * usm.setDatabase("mysql");
 * usm.getUsers();
 */

public class UserManager {

/**
 * This string "database" holds a value ("mysql" or"monodb") which specifies the corresponding database to connect.  
 */
	
	private String database;


	public String getDatabase() {
		return database;
	}


	public void setDatabase(String database) {
		this.database = database;
	}



	/**
	 * This method can be used to add a new user to the database.
	 *
	 * @param User object (com.testproblem.model.User) which needs to be added  
	 * @return boolean specifying success or error
	 */
	public boolean addNewUser(User user){
		
		boolean added =false;
		
		if(database.equalsIgnoreCase("mysql") || database.equals(null)){
				Connection conn=Dao_Mysql.getConnection();
				PreparedStatement pstmt;
				
				try{
					
					pstmt=conn.prepareStatement("insert into userinfo (name,email, ph, comment ) values (?,?,?,?)");
					pstmt.setString(1, user.getName());
					pstmt.setString(2, user.getEmail());
					pstmt.setLong(3, user.getPh());
					pstmt.setString(4, user.getComment());
					
					pstmt.executeUpdate();
					
					added=true;
					
				}catch(Exception ex){
					
					System.out.println("Exception while adding new user "+ex.toString());
					
				}
		
		}else if(database.equalsIgnoreCase("mongodb")){
			
				DB db=DAO_Mongodb.getConnection();
				DBCollection dbUserInfo=db.getCollection("userinfo");
				DBCollection dbCounter=db.getCollection("counters");
				
				int counter=getNextSequence(dbCounter);
				
				DBObject dbInsert=new BasicDBObject("_id",counter).append("name", user.getName()).append("email",user.getEmail()).append("ph",user.getPh()).append("comment", user.getComment());
				dbUserInfo.insert(dbInsert);
				
				added=true;
		}
		
		return added;
				
	}
	
	
	
	
	/**
	 * This method can be used to fetch all the users present in the database
	 *
	 * @param Nothing  
	 * @return List of users containing each User objects(com.testproblem.model.User) present in the database
	 */
	
	public List<User> getAllUsers(){
		
		ArrayList<User> alUser=new ArrayList<User>();
		
		if(database.equalsIgnoreCase("mysql") || database.equalsIgnoreCase("") || database.equals(null)){
		
				Connection conn=Dao_Mysql.getConnection();		
				PreparedStatement pstmt;
				ResultSet rs=null;				
				
				try{
					
					pstmt=conn.prepareStatement("select * from userinfo");					
					rs=pstmt.executeQuery();
					
					while(rs.next()){
						
						User user=new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setPh(rs.getLong("ph"));
						user.setComment(rs.getString("comment"));
						
						alUser.add(user);
					}					
					
					
				}catch(Exception ex){
					
					System.out.println("Exception while fething user "+ex.toString());
					
				}
			}else if(database.equalsIgnoreCase("mongodb")){
				
					DB db=DAO_Mongodb.getConnection();
					DBCollection dbCollection=db.getCollection("userinfo");
					DBCursor dbCursor=dbCollection.find();
					
					while(dbCursor.hasNext()){
						
						DBObject obj=dbCursor.next();
						User user=new User();

						user.setId((Integer)obj.get("_id"));
						user.setName((String)obj.get("name"));					
						user.setPh((Long)obj.get("ph") );	
						user.setEmail((String)obj.get("email"));
						user.setComment((String)obj.get("comment"));
						
						alUser.add(user);
					}
					
					
			}
				
		return alUser;
	}
	
	
	
	/**
	 * This method can be used to update a particular user in database
	 *
	 * @param User object (com.testproblem.model.User) which needs to be updated  
	 * @return boolean specifying the success or error
	 */
	
	public boolean updateUser(User user){
		
		boolean update=false;
		
		if(database.equalsIgnoreCase("mysql") || (database.equals(null))){
				Connection conn=Dao_Mysql.getConnection();
				PreparedStatement pstmt;
				
				
				
				try{
					
					pstmt=conn.prepareStatement("update userinfo set name=?, email=?, ph=?,comment=? where id=?" );
				
					pstmt.setString(1, user.getName());
					pstmt.setString(2, user.getEmail());
					pstmt.setLong(3, user.getPh());
					pstmt.setString(4, user.getComment());
					pstmt.setInt(5, user.getId());
					
					pstmt.executeUpdate();
					update=true;
					
					
				}catch(Exception ex){
					
					System.out.println("Exception while updating user "+ex.toString());
					
				}
				
		}else if(database.equalsIgnoreCase("mongodb")){
				
				DB db=DAO_Mongodb.getConnection();
				DBCollection dbUserInfo=db.getCollection("userinfo");
				
				BasicDBObject dbUpdate=new BasicDBObject();
				
				dbUpdate.append("$set" ,new BasicDBObject().append("name", user.getName()).append("email", user.getEmail()).append("ph",user.getPh()).append("comment",user.getComment()));
				
				BasicDBObject dbSearch=new BasicDBObject();
				dbSearch.append("_id", user.getId());
				
				
				dbUserInfo.update(dbSearch, dbUpdate);
				
				update =true;
				
		}
		
		return update;
	}
	
	
	/**
	 * This method can be used to delete a particular user from the database
	 *
	 * @param User ID which needs to be deleted  
	 * @return boolean specifying the success or error
	 */
	
	public boolean deleteUser(int id){
		
		boolean delete=false;
		
		
		if(database.equalsIgnoreCase("mysql") || (database.equals(null))){
				Connection conn=Dao_Mysql.getConnection();
				PreparedStatement pstmt;
				
				
				try{
					
					pstmt=conn.prepareStatement("delete from userinfo where id=?" );
					pstmt.setInt(1, id);			
					pstmt.executeUpdate();
					delete=true;
								
				}catch(Exception ex){
					
					System.out.println("Exception while deleting user "+ex.toString());
					
				}
		}else if(database.equalsIgnoreCase("mongodb")){
			
					DB db=DAO_Mongodb.getConnection();
					DBCollection dbUserInfo=db.getCollection("userinfo");
					
					BasicDBObject query=new BasicDBObject();
					query.append("_id", id);
					
					dbUserInfo.remove(query);
					
					delete=true;
		}
		
		return delete;
	}
	
	
	/**
	 * This method can be used to fetch all the users present in the database in a sorted manner
	 *
	 * @param Sorting criteria (e:g: if sorting to be done based on column "Name" and in Ascending order, 
	 * the String parameter will be "name ASC" )  
	 * @return List of users containing each User objects(com.testproblem.model.User) present in the database
	 */
	
	public List<User> getAllUsersSorted(String jtSorting){
		
		ArrayList<User> alUser=(ArrayList<User>) getAllUsers();
		
		String[] tempSort=jtSorting.split(" ");
		String columnSort=tempSort[0];
		String direction=tempSort[1];
				
		if(columnSort.equalsIgnoreCase("name")){
			Collections.sort(alUser,User.nameCompare);
			
			if(direction.equalsIgnoreCase("DESC")){
				//Reverse the List
				Collections.reverse(alUser);
			}
		}else if(columnSort.equalsIgnoreCase("email")){
			Collections.sort(alUser, User.emailCompare);
			
			if(direction.equalsIgnoreCase("DESC")){
				//Reverse the list
				Collections.reverse(alUser);
				
			}
		}else if(columnSort.equalsIgnoreCase("ph")){
			Collections.sort(alUser, User.phCompare);
			
			
			if(direction.equalsIgnoreCase("DESC")){
				//Reversing the list
				Collections.reverse(alUser);
			}
		}else if(columnSort.equalsIgnoreCase("comment")){
			
			Collections.sort(alUser,User.commentCompare);
		
			if(direction.equalsIgnoreCase("DESC")){
				Collections.reverse(alUser);
			}
		}
		
		return alUser;
	}
	




	
	/**
	 * This method can be used to search for a particular pattern 
	 *
	 * @param The Search pattern which needs to be matched and the column name against which the searching needs to perform   
	 * the String parameter will be "name ASC" )  
	 * @return List of users (search results) containing each User objects(com.testproblem.model.User) present in the database
	 */

	public List<User> getSearchResult(String search,String columnName){
		
		ArrayList<User> alUser=new ArrayList<User>();
		
		if(database.equalsIgnoreCase("mysql")){
				Connection conn=Dao_Mysql.getConnection();
				PreparedStatement pstmt;
				ResultSet rs=null;
				
				
				try{
					
					pstmt=conn.prepareStatement("select * from userinfo where "+columnName+" Like '"+search+"%'" );
				
					
					rs=pstmt.executeQuery();
					
					
					while(rs.next()){
						
						User user=new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setPh(rs.getLong("ph"));
						user.setComment(rs.getString("comment"));
						
						alUser.add(user);
					}
					
					
					
				}catch(Exception ex){
					
					System.out.println("Exception while fething user "+ex.toString());
					
				}
		
		}else if(database.equalsIgnoreCase("mongodb")){
			
				DB db =DAO_Mongodb.getConnection();
				DBCollection dbUserInfo=db.getCollection("userinfo");
				
				BasicDBObject dbQuery=new BasicDBObject();
				dbQuery.put(columnName, new BasicDBObject("$regex" , search+".*").append("$options", "i"));
				
				
				DBCursor dbCursor=dbUserInfo.find(dbQuery);
				
				
				while(dbCursor.hasNext()){
						
					DBObject obj=dbCursor.next();
					User user=new User();
					user.setId((Integer)obj.get("_id"));
					user.setName((String)obj.get("name"));
					user.setEmail((String)obj.get("email"));
					user.setComment((String)obj.get("comment"));
					user.setPh((Long)obj.get("ph"));
					
					alUser.add(user);
					
				}
				
		}
		
		
		return alUser;
	}
	

	/**
	 * This method can be used to get the next sequence of a particular column in MongoDB  
	 * Used to get the next sequence User ID             
	 * @param collection name where the counters are maintained  
	 *   
	 * @return the next sequence integer
	 */

	public int getNextSequence(DBCollection dbCounter){
		
		
		DBObject query=new BasicDBObject();
		query.put("_id","userid");
		
		DBObject change=new BasicDBObject("sequence_value",1);
		DBObject update =new BasicDBObject("$inc",change);
		
		DBObject response=dbCounter.findAndModify(query, update);		
		Double nextSeq=(Double)response.get("sequence_value");	
		
		int iNext=nextSeq.intValue();	
		
		return iNext;
	}


}
