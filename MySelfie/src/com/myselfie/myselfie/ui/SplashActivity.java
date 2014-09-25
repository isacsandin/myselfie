package com.myselfie.myselfie.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.myselfie.myselfie.R;

public class SplashActivity extends Activity {
	private static final String TAG = "SplashActivity";
	private boolean isResumed = false;
    private UiLifecycleHelper uiHelper;
    // Request code for reauthorization requests. 
    private static final int REAUTH_ACTIVITY_CODE = 100; 
    // Flag to represent if we are waiting for extended permissions
    private boolean pendingAnnounce = true;
    private static final List<String> PERMISSIONS = Arrays.asList(new String[]{"publish_stream","publish_actions"});
    private Session.StatusCallback callback = new Session.StatusCallback() {
         @Override
         public void call(Session session, SessionState state, Exception exception) {
             onSessionStateChange(session, state, exception);
         }
     };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		LoginButton mButtonLogin = (LoginButton) findViewById(R.id.authButton);
		mButtonLogin.setReadPermissions(Arrays.asList(
                new String[] {"email","public_profile","user_birthday",
                		"user_location","user_friends","user_hometown",
                		"user_photos"}));

        Session session = Session.getActiveSession();
		if(session!=null && session.isOpened()){
			Log.i(TAG, "Session valid, proceding to main activity");
			startActivity(new Intent(getBaseContext(), MainActivity.class));
		    finish();
		}
		

	}
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}
	
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
        isResumed = false;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case REAUTH_ACTIVITY_CODE:
            Session session = Session.getActiveSession();
            if (session != null) {
                session.onActivityResult(this, requestCode, resultCode, data);
            }
            break;
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (isResumed) {
	        // check for the OPENED state instead of session.isOpened() since for the
	        // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
	        if (state.equals(SessionState.OPENED)) {
	    	    if (pendingAnnounce) {
	    	        // Publish the action
	    	        handleAnnounce();
	    	    }				
	    	    startActivity(new Intent(getBaseContext(), MainActivity.class));
			    finish();
	          } else if (state.isClosed()) {
	              }
	         }
	}
	
	private void handleAnnounce() {  
	    pendingAnnounce = false;
	    Session session = Session.getActiveSession();

	    if (session == null || !session.isOpened()) {
	        return;
	    }

	    List<String> permissions = session.getPermissions();
	    if (!permissions.containsAll(PERMISSIONS)) {
	        pendingAnnounce = true; // Mark that we are currently waiting for confirmation of publish permissions 
	        session.addCallback(callback); 
	        requestPublishPermissions(this, session, PERMISSIONS, REAUTH_ACTIVITY_CODE);
	        return;
	    }
	    // TODO: Publish the post. You would need to implement this method to actually post something
	    
	}

	public static void requestPublishPermissions(Activity activity, Session session, List<String> permissions,  
		    int requestCode) {
		    if (session != null) {
		        Session.NewPermissionsRequest reauthRequest = new Session.NewPermissionsRequest(activity, permissions)
		        .setRequestCode(requestCode);
		        session.requestNewPublishPermissions(reauthRequest);
		    }
	}

}