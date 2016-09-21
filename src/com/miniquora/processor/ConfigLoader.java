package com.miniquora.processor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;

import com.miniquora.model.Interest;
import com.miniquora.service.LoginService;

public class ConfigLoader implements InitializingBean {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	LoginService loginService;
	
	public SessionFactory getSessionFactory() {
        return this.sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
	}
	
	public void afterPropertiesSet() throws Exception {
		System.out.println("Inside afterPropertiesSet method.");
		initiateInterestTable();
		
	}
	
	private void initiateInterestTable(){
		Interest interest1 = new Interest();
		interest1.setInterestName("Sports");
		
		Interest interest2 = new Interest();
		interest2.setInterestName("Software Engineering");
		
		Interest interest3 = new Interest();
		interest3.setInterestName("Computers");
		
		Interest interest4 = new Interest();
		interest4.setInterestName("Shopping");
		
		//loginService.createUserProfile(null, 0, null, null);
		
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		//Saving user for the first time
		session.save(interest1);
		session.save(interest2);
		session.save(interest3);
		session.save(interest4);
		session.getTransaction().commit();
		session.close();
	}

}