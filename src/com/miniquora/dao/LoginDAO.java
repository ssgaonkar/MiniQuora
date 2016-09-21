package com.miniquora.dao;

import java.io.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.miniquora.constants.Configuration;
import com.miniquora.model.Interest;
import com.miniquora.model.Profile;
import com.miniquora.model.UserDetails;

public class LoginDAO {
	
	@Autowired
	Configuration config;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
        return this.sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
	}
	
	public UserDetails isUserValid(String userName, String password){
		String HQL_QUERY = "from UserDetails where userName = :userName AND password = :password";
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		List<UserDetails> users = (List<UserDetails>)query.list();
		UserDetails user = null;
		boolean isUserValidFlag = false;
		if(users!= null & users.size() == 1){
			isUserValidFlag = true;
			user = users.get(0);
		}
		session.getTransaction().commit();
		return user;
	}

	public int registerUser(Map<String, Object> requestParams){
		
		//Creating user pojo
		UserDetails user = new UserDetails();
		user.setUserName((String)requestParams.get("userName"));
		user.setPassword((String)requestParams.get("password"));
		
		boolean isUserValidFlag = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			//Saving user for the first time
			session.save(user);
			session.getTransaction().commit();
			session.close();
			isUserValidFlag = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isUserValidFlag = false;
		}
		if(isUserValidFlag){
			return user.getUserId();
		}
		else{
			return -1;
		}
	}

	public boolean createUserProfile(Map<String, Object> requestParams, int registeredUserId, MultipartFile file, String rootPath, String[] interestIds){
		
		boolean isProfileSavedFlag = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			//Fetching user 
			UserDetails user = session.get(UserDetails.class, registeredUserId);
			
			//Creating Profile pojo
			Profile userProfile = new Profile();
			userProfile.setUser(user);
			userProfile.setBioInfo((String)requestParams.get("bioInfo"));
			userProfile.setProfilePicFileName(file.getOriginalFilename());
			
			//Prepare Interest ArrayList
			ArrayList<Interest> interestList = new ArrayList<Interest>();
			for(String interestId : interestIds){
				Interest interest = session.get(Interest.class, Integer.parseInt(interestId));
				interestList.add(interest);
			}
			userProfile.setProfileInterests(interestList);
			
			//Saving user file
			saveFile(file, user, rootPath);
			
			//Updating user object by setting user profile.
			user.setProfile(userProfile);
			
			//Saving user and profile objects.
			session.update(user);
			session.save(userProfile);
			session.getTransaction().commit();
			session.close();
			isProfileSavedFlag = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isProfileSavedFlag = false;
		}
		return isProfileSavedFlag;
	}
	
	public boolean updateUserProfile(Map<String, Object> requestParams, int registeredUserId, MultipartFile file, String rootPath, String[] interestIds){
		
		boolean isProfileSavedFlag = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			//Fetching user 
			UserDetails user = session.get(UserDetails.class, registeredUserId);
			
			//Creating Profile pojo
			Profile userProfile = user.getProfile();
			//userProfile.setUser(user);
			userProfile.setBioInfo((String)requestParams.get("bioInfo"));
			
			if(file.getOriginalFilename() != ""){
				System.out.println("file not null");
				userProfile.setProfilePicFileName(file.getOriginalFilename());
				//Saving user file
				saveFile(file, user, rootPath);
			}
			
			//Prepare Interest ArrayList
			ArrayList<Interest> interestList = new ArrayList<Interest>();
			userProfile.setProfileInterests(interestList);
			session.update(userProfile);
			
			for(String interestId : interestIds){
				Interest interest = session.get(Interest.class, Integer.parseInt(interestId));
				interestList.add(interest);
			}
			userProfile.setProfileInterests(interestList);
			
			//Updating user object by setting user profile.
			user.setProfile(userProfile);
			
			//Saving user and profile objects.
			session.update(user);
			session.update(userProfile);
			session.getTransaction().commit();
			session.close();
			isProfileSavedFlag = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isProfileSavedFlag = false;
		}
		return isProfileSavedFlag;
	}	

	public void saveFile(MultipartFile file, UserDetails user, String rootPath) throws Exception{
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                //File dir = new File(rootPath + File.separator + "resources" + File.separator + "profilepics" + File.separator + user.getUserName());
                File dir = new File(config.getProfilePicturePath() + File.separator + "resources" + File.separator + "profilepics" + File.separator + user.getUserName());
                
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                System.out.println("Successfully uploaded file=" + file.getName());
            } catch (Exception e) {
                System.out.println("Failed to upload " + file.getName() + " \n " + e.getMessage());
                throw e;
            }
        }
	}
	
	public UserDetails getUserFromId(int userId){
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		//Fetching user 
		UserDetails user = session.get(UserDetails.class, userId);
		session.getTransaction().commit();
		session.close();
		return user;
	}
	
	public ArrayList<Interest> getInterestList(){
		ArrayList<Interest> interests = new ArrayList<Interest>();
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			//Fetching user
			String HQL_QUERY = "from Interest";
			Query query = session.createQuery(HQL_QUERY);
			interests = (ArrayList<Interest>)query.list();
			session.getTransaction().commit();
			session.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		return interests;
	}
	
	public String getProfilePicture(UserDetails user){
		String encodedString = "";
		try{
			File file = new File(config.getProfilePicturePath() + File.separator + "resources" + File.separator + "profilepics" + File.separator + user.getUserName() + File.separator + user.getProfile().getProfilePicFileName());
			FileInputStream fis=new FileInputStream(file);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			int b;
			byte[] buffer = new byte[1024];
			while((b=fis.read(buffer))!=-1){
			   bos.write(buffer,0,b);
			}
			byte[] fileBytes=bos.toByteArray();
			fis.close();
			bos.close();
			byte[] encoded=Base64.encodeBase64(fileBytes);
			encodedString = new String(encoded);
		}
		catch(Exception e){
			System.out.println("Failed to fetch file. \n " + e.getMessage());
		}
		return encodedString;
	}
	
}
