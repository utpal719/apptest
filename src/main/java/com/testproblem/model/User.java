package com.testproblem.model;

import java.util.Comparator;

/**
 * This Model object is corresponds to a user
 * Fields ID,Name,EMAIL,PH,COMMENT holds corresponding informations  
 */

public class User {

	private int id;
	private String name;
	private String email;
	private long ph;
	private String comment;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPh() {
		return ph;
	}
	public void setPh(long ph) {
		this.ph = ph;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	/**
	 * This static inner class is used to compare name of two user objects. 
	 * Used for sorting based on column "Name" 
	 */
	
	public static Comparator<User> nameCompare= new Comparator<User>() {

		public int compare(User o1, User o2) {
			User u1=(User)o1;
			User u2=(User)o2;
			
			return u1.getName().compareTo(u2.getName());
			
		}
		
	};
	
	
	/**
	 * This static inner class is used to compare comments of two user objects. 
	 * Used for sorting based on column "Comment" 
	 */
	
	public static Comparator<User> commentCompare= new Comparator<User>() {

		public int compare(User o1, User o2) {
			User u1=(User)o1;
			User u2=(User)o2;
			
			return u1.getComment().compareTo(u2.getComment());
			
		}
		
	};
	
	/**
	 * This static inner class is used to compare email of two user objects. 
	 * Used for sorting based on column "Email" 
	 */
	
	public static Comparator<User> emailCompare= new Comparator<User>() {

		public int compare(User o1, User o2) {
			User u1=(User)o1;
			User u2=(User)o2;
			
			return u1.getEmail().compareTo(u2.getEmail());
			
		}
		
	};
	
	
	/**
	 * This static inner class is used to compare "ph number" of two user objects. 
	 * Used for sorting based on column "Ph" 
	 */
	
	public static Comparator<User> phCompare= new Comparator<User>() {

		public int compare(User o1, User o2) {
			User u1=(User)o1;
			User u2=(User)o2;
			
			if(u1.getPh() == u2.getPh()){
				
				return 0;
			}else if(u1.getPh() > u2.getPh()){
				return 1;			
			}else {
				return -1;
				
			}

		}
		
	};
	
	
}
