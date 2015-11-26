package com.testproblem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.testproblem.util.JsonJtableResponse;
import com.testproblem.model.User;
import com.testproblem.util.UserManager;

/**
 * This controller is responsible to manage any request for the apptest project  
*/

@RestController
@RequestMapping("users/service")
public class ApptestController {

	/**
	 * This method can be used to fetch all the users from the database
	 * @param String sorting, searching criteria and the column against which the search should perform (can be null)  
	 * @return returns a map (refer to JsonJtableResponse class) containing the results and a flag variable (success or failure)
	 */  
	 
	
	@RequestMapping(value="/listusers", method = RequestMethod.POST)
	public JsonJtableResponse getUser(String jtSorting,String search,String columnsearch){
		
		UserManager usm=new UserManager();
		usm.setDatabase("mysql");
		ArrayList<User> alUserList=new ArrayList<User>();
		
		if(jtSorting == null){
			
			alUserList=(ArrayList<User>)usm.getAllUsers();
		}else{
			alUserList=(ArrayList<User>)usm.getAllUsersSorted(jtSorting);
		}
		
		if(search != null){
			
			alUserList=(ArrayList<User>)usm.getSearchResult(search,columnsearch);
		}
		
		return new JsonJtableResponse().ok(alUserList);
	}
	
	
	/**
	 * This method can be used to add a new user to the database
	 * @param New User object  
	 * @return returns the newly added user
	 */  
	
	@RequestMapping(value="/adduser" , method =RequestMethod.POST)
	public @ResponseBody User addUser(@RequestBody User user){
				
		UserManager usm=new UserManager();
		usm.setDatabase("mysql");
		usm.addNewUser(user);
		return user;	
	}
	
	/**
	 * This method can be used to update a user
	 * @param updated user object  
	 * @return returns a map (refer to JsonJtableResponse class) containing the updated user list and a flag variable (success or failure)
	 */  
	
	@RequestMapping(value="/updateuser", method= RequestMethod.POST)
	public @ResponseBody JsonJtableResponse updateUser(User user){
		
		UserManager usm=new UserManager();
		usm.setDatabase("mysql");
		boolean update=usm.updateUser(user);
		
		if(update){
			
			return new JsonJtableResponse().ok();
		}else{
			return new JsonJtableResponse().error("Error while updating the user");
			
		}
		
		
	}
	
	
	/**
	 * This method can be used to delete a user
	 * @param int UserId to be deleted  
	 * @return returns a map (refer to JsonJtableResponse class) containing the updated results and a flag variable (success or failure)
	 */  

	@RequestMapping(value="/deleteuser", method= RequestMethod.POST)
	public @ResponseBody JsonJtableResponse deleteUser(int id){
		
		UserManager usm=new UserManager();
		usm.setDatabase("mysql");
		
		boolean delete=usm.deleteUser(id);
		
		if(delete){
			
			return new JsonJtableResponse().ok();
		}else{
			return new JsonJtableResponse().error("Error while deleting the user");
			
		}
		
		
	}
	
	
	/**
	 * This method can be used for testing purpose to check the JSON response in browser
	 * @param Nothing  
	 * @return returns list of users
	 * Example uses:
	 * URI : http://localhost:8080/apptest/users/service/viewjson
	 */  

	@RequestMapping(value="/viewjson", method= RequestMethod.GET)
	public @ResponseBody List<User> viewResponse(){
		
		UserManager usm=new UserManager();
		usm.setDatabase("mysql");
		
		return usm.getAllUsers();		
		
	}
	
}
