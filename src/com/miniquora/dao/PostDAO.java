package com.miniquora.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.miniquora.constants.CountType;
import com.miniquora.model.Interest;
import com.miniquora.model.Post;
import com.miniquora.model.Profile;
import com.miniquora.model.UserDetails;

public class PostDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
        return this.sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
	}
	
	public boolean createPost(Map<String, Object> requestParams, int registeredUserId){
		
		//Creating user pojo
		Post post = new Post();
		post.setPostContent((String)requestParams.get("postContent"));
		post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		post.setUpCount(0);
		post.setDownCount(0);
		post.setHappyCount(0);
		post.setSadCount(0);
		post.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		
		boolean isPostCreated = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			UserDetails user = session.get(UserDetails.class, registeredUserId);
			Profile userProfile = user.getProfile();
			post.setProfile(userProfile);
			
			Interest interest = session.get(Interest.class, Integer.parseInt((String)requestParams.get("postInterest")));
			post.setInterest(interest);
			
			//Saving post for the first time
			session.save(post);
			session.getTransaction().commit();
			session.close();
			isPostCreated = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isPostCreated = false;
		}
		if(isPostCreated){
			return true;
		}
		else{
			return false;
		}
	}

	public void updatePost(Map<String, Object> requestParams){
	
		boolean isPostUpdated = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			Post post = session.get(Post.class, Integer.parseInt((String)requestParams.get("postId")));
			post.setPostContent((String)requestParams.get("postContent"));
			post.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
			
			Interest interest = session.get(Interest.class, Integer.parseInt((String)requestParams.get("postInterest")));
			post.setInterest(interest);
			
			session.update(post);
			session.getTransaction().commit();
			session.close();
			isPostUpdated = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isPostUpdated = false;
		}
	}

	public void deletePost(Map<String, Object> requestParams){		
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Post post = session.get(Post.class, Integer.parseInt((String)requestParams.get("postId")));
		session.delete(post);
		session.getTransaction().commit();
		session.close();			
	}
	
	public void incrementCount(Map<String, Object> requestParams, CountType incrementType){
		
		boolean isPostUpdated = false;
		try{
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			
			Post post = session.get(Post.class, Integer.parseInt((String)requestParams.get("postId")));
			//incrementing count based on type
			switch(incrementType){
				case UP:
					post.setUpCount(post.getUpCount()+1);
					break;
				case DOWN:
					post.setDownCount(post.getDownCount()+1);
					break;
				case HAPPY:
					post.setHappyCount(post.getHappyCount()+1);
					break;
				case SAD:
					post.setSadCount(post.getSadCount()+1);
					break;
				default:
					throw new Exception("Invalid Increment Type");
			}
			
			//post.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
			
			session.update(post);
			session.getTransaction().commit();
			session.close();
			isPostUpdated = true;
		}
		catch(Exception e){
			e.printStackTrace();
			isPostUpdated = false;
		}
	}

	public List<Post> getPersonalPosts(UserDetails user){
		
		String HQL_QUERY = "from Post where profile = :userProfile ORDER BY lastUpdatedAt DESC";
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("userProfile", user.getProfile());
		
		List<Post> posts = new ArrayList<Post>();
		posts = (List<Post>)query.list();
		
		if(posts!= null & posts.size() > 0){
			System.out.println("Personal Posts : " + posts.size());
		}
		session.getTransaction().commit();
		
		return posts;
	}
	
	public List<Post> getNewsFeed(UserDetails user){
		String strInterests = prepareInterestString(user.getProfile().getProfileInterests());
		String SQL_QUERY = "select * from Post where PROFILE_ID != :userProfileId AND INTEREST_ID NOT IN ( :profileInterests ) ORDER BY lastUpdatedAt DESC";
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		
		SQLQuery query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("userProfileId", user.getProfile().getProfileId());
		query.setParameter("profileInterests", strInterests);
		query.addEntity(Post.class);
		
		List<Post> posts = (List<Post>)query.list();
		//System.out.println(posts.get(0).getProfile().getUser().getUserName());
		session.getTransaction().commit();
		return posts;
	}
	
	private String prepareInterestString(List<Interest> interestList){
		String strInterest = "";
		for(Interest interest : interestList){
			if(strInterest.equals(""))
				strInterest = "'" +interest.getInterestId() + "'";
			else
				strInterest += ", '" + interest.getInterestId() + "'";
		}
		
		return strInterest;
	}

}
