package com.miniquora.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.miniquora.constants.Configuration;
import com.miniquora.model.UserDetails;
import com.miniquora.service.LoginService;
import com.miniquora.service.PostService;

@Controller
public class ProfileController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	Configuration config;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView redirectToLoginPage(){
		return new ModelAndView("login");
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView validateUser(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request){
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		
		UserDetails user = loginService.isUserValid(userName, password);
		if(user != null){
			httpSession.setAttribute("USERID", user.getUserId());
			model = new ModelAndView("personalposts");
			model.addObject("USEROBJ", user);
			model.addObject("INTEREST_LIST", loginService.getInterestList());
			model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
			model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
			//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		}
		else{
			model = new ModelAndView("login");
		}
		return model;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView redirectToRegisterPage(){
		return new ModelAndView("registeruser");
		
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUser(@RequestParam Map<String, Object> requestParams, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = loginService.registerUser(requestParams);
		if(registeredUserId != -1){
			model = new ModelAndView("createprofile");
			model.addObject("INTEREST_LIST", loginService.getInterestList());
			httpSession.setAttribute("USERID", registeredUserId);
		}
		else{
			model = new ModelAndView("registeruser");
		}
		return model;
	}
	
	@RequestMapping(value="/createuserprofile", method=RequestMethod.POST)
	public ModelAndView createUserProfile(@RequestParam Map<String, Object> requestParams,@RequestParam("file") MultipartFile file, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		//String rootPath = request.getServletContext().getRealPath("WEB_INF");
		String rootPath = request.getServletContext().getRealPath(File.separator);
		System.out.println("Context Path: " + rootPath);
		String[] interestIds = request.getParameterValues("profileInterests");
		if(loginService.createUserProfile(requestParams, registeredUserId, file, rootPath, interestIds)){
			model = new ModelAndView("personalposts");
			UserDetails user = loginService.getUserFromId(registeredUserId);
			model.addObject("USEROBJ", user);
			model.addObject("INTEREST_LIST", loginService.getInterestList());
			model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
			model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
			//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		}
		else{
			model = new ModelAndView("createprofile");
		}
		return model;
	}

	@RequestMapping(value="/redirecttoeditprofile", method=RequestMethod.GET)
	public ModelAndView redirectToEditProfile(HttpServletRequest request){
		ModelAndView model = new ModelAndView("editprofile");
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		UserDetails user = loginService.getUserFromId(registeredUserId);
		model.addObject("USEROBJ", user);
		model.addObject("INTEREST_LIST", loginService.getInterestList());
		model.addObject("PROFILE_INTEREST_LIST", user.getProfile().getProfileInterests());
		return model;
		
	}
	
	@RequestMapping(value="/updateuserprofile", method=RequestMethod.POST)
	public ModelAndView updateUserProfile(@RequestParam Map<String, Object> requestParams,@RequestParam("file") MultipartFile file, HttpServletRequest request){
		//File validation remaining
		ModelAndView model = null;
		HttpSession httpSession = request.getSession(true);
		int registeredUserId = (Integer)httpSession.getAttribute("USERID");
		//String rootPath = request.getServletContext().getRealPath("WEB_INF");
		String rootPath = request.getServletContext().getRealPath(File.separator);
		System.out.println("Context Path: " + rootPath);
		String[] interestIds = request.getParameterValues("profileInterests");
		if(loginService.updateUserProfile(requestParams, registeredUserId, file, rootPath, interestIds)){
			model = new ModelAndView("personalposts");
			UserDetails user = loginService.getUserFromId(registeredUserId);
			model.addObject("USEROBJ", user);
			model.addObject("INTEREST_LIST", loginService.getInterestList());
			model.addObject("PERSONALPOSTS", postService.getPersonalPosts(user));
			model.addObject("PROFILEPICTURE", loginService.getProfilePicture(user));
			//model.addObject("PROFILEPICTUREPATH", config.getProfilePicturePath());
		}
		else{
			model = new ModelAndView("createprofile");
		}
		return model;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request){
		ModelAndView model = new ModelAndView("login");;
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		return model;
	}
	
}
