package com.miniquora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.*;

import java.util.*;

@Entity
public class Profile {

	@Id @GeneratedValue
	int profileId;
	
	String profilePicFileName;
	String bioInfo;
	
	@OneToOne
	UserDetails user;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="PROFILE_INTEREST", joinColumns = @JoinColumn(name="PROFILE_ID"), inverseJoinColumns = @JoinColumn(name="INTEREST_ID"))
	List<Interest> profileInterests;
	
	//@OneToMany(fetch = FetchType.EAGER, mappedBy="profile")
	@OneToMany(mappedBy="profile")
	List<Post> posts = new ArrayList<Post>();
	
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Interest> getProfileInterests() {
		return profileInterests;
	}
	public void setProfileInterests(List<Interest> profileInterests) {
		this.profileInterests = profileInterests;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	
	public String getBioInfo() {
		return bioInfo;
	}
	public void setBioInfo(String bioInfo) {
		this.bioInfo = bioInfo;
	}
	public String getProfilePicFileName() {
		return profilePicFileName;
	}
	public void setProfilePicFileName(String profilePicFileName) {
		this.profilePicFileName = profilePicFileName;
	}
	
}
