package com.myselfie.myselfie.model;

import java.util.Date;

public class User {
	
	private Integer facebookID;
	private String name;
	private String email;
	private String gender;
	private Date birthDate;
	private String token;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(Integer facebookID) {
		this.facebookID = facebookID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public User() {
	}

}
