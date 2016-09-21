package com.miniquora.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.miniquora.dao.LoginDAO;
import com.miniquora.model.Interest;
import com.miniquora.model.UserDetails;


public class LoginService {
	
	@Autowired
	LoginDAO loginDAO;
	
	public UserDetails isUserValid(String userName, String password){
		return loginDAO.isUserValid(userName, password);	
	}
	
	public int registerUser(Map<String, Object> requestParams){
		return loginDAO.registerUser(requestParams);
	}
	
	public boolean createUserProfile(Map<String, Object> requestParams, int registeredUserId, MultipartFile file, String rootPath, String[] interestIds){
		return loginDAO.createUserProfile(requestParams, registeredUserId, file, rootPath, interestIds);
	}
	
	public boolean updateUserProfile(Map<String, Object> requestParams, int registeredUserId, MultipartFile file, String rootPath, String[] interestIds){
		return loginDAO.updateUserProfile(requestParams, registeredUserId, file, rootPath, interestIds);
	}
	
	public UserDetails getUserFromId(int userId){
		return loginDAO.getUserFromId(userId);
	}
	
	public ArrayList<Interest> getInterestList(){
		return loginDAO.getInterestList();
	}
	
	public String getProfilePicture(UserDetails user){
		return loginDAO.getProfilePicture(user);
	}
	
}
