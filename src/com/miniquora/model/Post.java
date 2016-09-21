package com.miniquora.model;

import javax.persistence.*;

import java.util.*;
import java.sql.Timestamp;

@Entity
public class Post {
	@Id @GeneratedValue
	int postId;
	
	String postContent;
	int upCount;
	int downCount;
	int happyCount;
	int sadCount;
	
	Timestamp createdAt;
	Timestamp lastUpdatedAt;
	
	@ManyToOne
	@JoinColumn(name="PROFILE_ID")
	Profile profile;
	
	@ManyToOne
	@JoinColumn(name="INTEREST_ID")
	Interest interest; 
	
	public Interest getInterest() {
		return interest;
	}
	public void setInterest(Interest interest) {
		this.interest = interest;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public int getUpCount() {
		return upCount;
	}
	public void setUpCount(int upCount) {
		this.upCount = upCount;
	}
	public int getDownCount() {
		return downCount;
	}
	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}
	public int getHappyCount() {
		return happyCount;
	}
	public void setHappyCount(int happyCount) {
		this.happyCount = happyCount;
	}
	public int getSadCount() {
		return sadCount;
	}
	public void setSadCount(int sadCount) {
		this.sadCount = sadCount;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	
	
}
