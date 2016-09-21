package com.miniquora.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.miniquora.constants.Configuration;
import com.miniquora.constants.CountType;
import com.miniquora.model.UserDetails;
import com.miniquora.service.LoginService;
import com.miniquora.service.PostService;

@Controller
public class PostController {
	@Autowired
	PostService postService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	Configuration config;
	
	@RequestMapping(value="/createpost", method=RequestMethod.POST)
	public ModelAndView createPost(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		if(postService.createPost(requestParams, registeredUserId)){
			model = new ModelAndView("personalposts");
			model.addObject("INTEREST_LIST", loginService.getInterestList());
			UserDetails user = loginService.getUserFromId(registeredUserId);
			model.addObject("USEROBJ", user);
			model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
			model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
			//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		}
		else{
			model = new ModelAndView("personalposts");
		}
		return model;
	}
	
	@RequestMapping(value="/updateordeletepost", method=RequestMethod.POST)
	public ModelAndView updateOrDeletePost(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = new ModelAndView("personalposts");
		postService.updateOrDeletePost(requestParams);
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("INTEREST_LIST", loginService.getInterestList());
		model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
		model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
		return model;
		
	}
	
	@RequestMapping(value="/redirecttopersonalposts", method=RequestMethod.GET)
	public ModelAndView redirectToPersonalPosts(HttpServletRequest request){
		ModelAndView model = new ModelAndView("personalposts");
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("INTEREST_LIST", loginService.getInterestList());
		model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
		model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
		return model;
		
	}
	
	
	@RequestMapping(value="/incrementcount", method=RequestMethod.POST)
	public ModelAndView incrementCount(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = new ModelAndView("personalposts");
		postService.incrementCount(requestParams);
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("INTEREST_LIST", loginService.getInterestList());
		model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
		model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
		return model;
	}
	
	

	@RequestMapping(value="/personalposts", method=RequestMethod.GET)
	public ModelAndView getPersonalPosts(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		model = new ModelAndView("personalposts");
		model.addObject("INTEREST_LIST", loginService.getInterestList());
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
		model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
		//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		return model;
	}
	
	@RequestMapping(value="/newsfeed", method=RequestMethod.GET)
	public ModelAndView getNewsFeed(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		model = new ModelAndView("newsfeed");
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("NEWSFEED", postService.getNewsFeed(user));
		model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
		//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		return model;
	}
	
}
