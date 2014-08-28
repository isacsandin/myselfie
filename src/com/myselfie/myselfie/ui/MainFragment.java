package com.myselfie.myselfie.ui;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.myselfie.myselfie.R;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;

	TextView profileInformation = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_first, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		profileInformation = (TextView) view.findViewById(R.id.profileText);
		authButton.setFragment(this);
		try {
	        openActiveSession(this, true, callback , Arrays.asList(
	                new String[] {"email","public_profile","user_birthday","user_location"}));
	    }
	    catch (Exception e) {
	        Log.e(TAG,"exception",e);
	    }
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private static Session openActiveSession(Fragment fragment, boolean allowLoginUI, StatusCallback callback, List<String> permissions) {
	    OpenRequest openRequest = new OpenRequest(fragment).setPermissions(permissions).setCallback(callback);
	    Session session = new Builder(fragment.getActivity()).build();
	    if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) {
	        Session.setActiveSession(session);
	        session.openForRead(openRequest);
	        return session;
	    }
	    return null;
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
	        Request request = Request.newMeRequest(session,new Request.GraphUserCallback() {
	                    @Override
	                    public void onCompleted(GraphUser user,
	                            Response response) {
	                        if (user != null) {
	                            profileInformation.setText(user.getInnerJSONObject().toString());

	                        }
	                    }
	                });
	        Request.executeBatchAsync(request);
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
			profileInformation.setText("");
		}
	}
	
}
