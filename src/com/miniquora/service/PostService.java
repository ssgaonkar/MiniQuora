package com.miniquora.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.miniquora.constants.CountType;
import com.miniquora.dao.PostDAO;
import com.miniquora.model.Post;
import com.miniquora.model.UserDetails;


public class PostService {
	@Autowired
	PostDAO postDAO;
	
	public boolean createPost(Map<String, Object> requestParams, int registeredUserId){
		return postDAO.createPost(requestParams, registeredUserId);
	}
	
	public void updateOrDeletePost(Map<String, Object> requestParams){
		if (requestParams.get("btnUpdatePost") != null) {
			postDAO.updatePost(requestParams);
		} else if (requestParams.get("btnDeletePost") != null) {
			postDAO.deletePost(requestParams);
		}	
	}
	
	public void incrementCount(Map<String, Object> requestParams){
		if (requestParams.get("btnIncrementUpCount") != null) {
			postDAO.incrementCount(requestParams, CountType.UP);
		} else if (requestParams.get("btnIncrementDownCount") != null) {
			postDAO.incrementCount(requestParams, CountType.DOWN);
		}else if (requestParams.get("btnIncrementHappyCount") != null) {
			postDAO.incrementCount(requestParams, CountType.HAPPY);
		} else if (requestParams.get("btnIncrementSadCount") != null) {
			postDAO.incrementCount(requestParams, CountType.SAD);
		}		
	}
	
	public List<Post> getNewsFeed(UserDetails user){
		return postDAO.getNewsFeed(user);
	}
	
	public List<Post> getPersonalPosts(UserDetails user){
		return postDAO.getPersonalPosts(user);
	}
	
}
