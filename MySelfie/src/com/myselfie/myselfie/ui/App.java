package com.myselfie.myselfie.ui;

import android.app.Application;
import android.content.Context;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.utils.Logger;


public class App extends Application {

	private static App instance;

	public App() {
		super();
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		Logger.DEBUG_WITH_STACKTRACE = true;
		
		Permission[] permissions = new Permission[] {	
        	    Permission.EMAIL,
        	    Permission.USER_BIRTHDAY,
        	    Permission.USER_LOCATION,
        	    Permission.USER_FRIENDS,
        	    Permission.USER_HOMETOWN,
        	    Permission.PUBLISH_ACTION,
        	    Permission.USER_PHOTOS
        	};
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
        .setAppId(getString(R.string.app_id))
        .setNamespace(getString(R.string.app_namespace))
        .setPermissions(permissions)
        .build();
 
        SimpleFacebook.setConfiguration(configuration);
	}

	public static App getApp() {
		return instance;
	}

	public static Context getContext() {
		return instance;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
