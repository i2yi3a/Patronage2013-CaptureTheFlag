package com.blstream.ctf2.services;

import android.content.Context;

import com.blstream.ctf2.storage.dao.UserDAO;
import com.blstream.ctf2.storage.entity.User;

/**
 * 
 * @author Marcin Sareło
 *
 */
public class UserServices extends Services {
	private UserDAO uDAO;
	
	public UserServices(Context context) {
		this.uDAO = new UserDAO(context);
	}
	
	public User addNewPlayer(String name, String password){
		User newUser = new User();
		newUser.setToken(registryNewUser(name, password));
		newUser.setName(name);

		newUser=uDAO.createUser(newUser);	
		return newUser;
	}
	
	public String registryNewUser(String name, String password) {
		String token="";	
		//TODO implement REST registration 
		//token= doSomeMagic(name,password);
		return token;
	} 
	
	public User updateUser(User user) {

		return uDAO.update(user);
	}
	

}
