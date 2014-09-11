package com.myselfie.myselfie.model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("id")
	private Integer id;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("gender")
	private String gender;

	@SerializedName("birthday")
	private Date birthday;

	@SerializedName("token")
	private String token;

	@SerializedName("locale")
	private String locale;

	@SerializedName("hometown")
	private String hometown;

	@SerializedName("location")
	private String location;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy").create();
		return gson.toJson(this);
	}

}
