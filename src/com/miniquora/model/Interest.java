package com.miniquora.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class Interest {

	@Id @GeneratedValue
	int interestId;
	
	String interestName;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="profileInterests")
	List<Profile> profiles = new ArrayList<Profile>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="interest")
	List<Post> posts = new ArrayList<Post>();
	
	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}
	
}
