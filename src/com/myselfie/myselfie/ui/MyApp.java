package com.myselfie.myselfie.ui;

import android.app.Application;
import android.content.Context;

import com.myselfie.myselfie.R;
import com.myselfie.myselfie.utils.Utils;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.utils.Logger;


public class MyApp extends Application {

	private static MyApp instance;

	public MyApp() {
		super();
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Utils.loadFonts();
		
		Logger.DEBUG_WITH_STACKTRACE = true;
		
		Permission[] permissions = new Permission[] {	
        	    Permission.EMAIL,
        	    Permission.USER_BIRTHDAY,
        	    Permission.USER_LOCATION,
        	    Permission.USER_FRIENDS,
        	    Permission.USER_HOMETOWN,
        	    Permission.PUBLISH_ACTION
        	};
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
        .setAppId(getString(R.string.app_id))
        .setNamespace(getString(R.string.app_namespace))
        .setPermissions(permissions)
        .build();
 
        SimpleFacebook.setConfiguration(configuration);
	}

	public static MyApp getApp() {
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
