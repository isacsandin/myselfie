package com.myselfie.myselfie.model;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserilizer implements JsonDeserializer<User> {

	  @Override
	  public User deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
	      throws JsonParseException {
	
		JsonObject jsonObject = json.getAsJsonObject();
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		User user = new User();
		user.setName(jsonObject.get("name").getAsString());
		user.setEmail(jsonObject.get("email").getAsString());
		user.setGender(jsonObject.get("gender").getAsString());
		user.setLocale(jsonObject.get("locale").getAsString());
		user.setId(jsonObject.get("id").getAsInt());
		try {
			user.setBirthday(format.parse(jsonObject.get("birthday").getAsString()));
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
		JsonObject jo = jsonObject.get("hometown").getAsJsonObject();
		if(jo != null)user.setHometown(jo.get("name").getAsString());
		
		jo = jsonObject.get("location").getAsJsonObject();
		if(jo != null)user.setLocation(jo.get("name").getAsString());

	    return user;
	  }
	}
