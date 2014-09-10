package com.myselfie.myselfie.ui;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.myselfie.myselfie.R;
import com.myselfie.myselfie.service.ServiceTask;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "MainActivity";
    private static final int SPLASH = 0;
    private static final int MAIN = 1;
    private static final int SETTINGS = 2;
    private static final int FRAGMENT_COUNT = SETTINGS +1;

    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    private MenuItem settings;
	private boolean isResumed = false;
    private SimpleFacebook mSimpleFacebook;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        FragmentManager fm = getSupportFragmentManager();
        fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
        fragments[MAIN] = fm.findFragmentById(R.id.mainFragment);
        fragments[SETTINGS] = fm.findFragmentById(R.id.settingsFragment);
      
        FragmentTransaction transaction = fm.beginTransaction();
        for(int i = 0; i < fragments.length; i++) {
            transaction.hide(fragments[i]);
        }
        transaction.commit();
        
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        Session session = mSimpleFacebook.getSession();

        if (session != null && session.isOpened()) {
            // if the session is already open, try to show the selection fragment
            showFragment(MAIN, false);
        } else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
            showFragment(SPLASH, false);
        }
        
        
       
    }

    @Override
    public void onResume() {
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        super.onResume();
        isResumed = true;

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        isResumed = false;

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be launched into.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    	mSimpleFacebook.getSession().addCallback(callback);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Session session = mSimpleFacebook.getSession();

        if (session != null && session.isOpened()) {
            // if the session is already open, try to show the selection fragment
            showFragment(MAIN, false);
        } else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
            showFragment(SPLASH, false);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // only add the menu when the selection fragment is showing
        if (fragments[MAIN].isVisible()) {
            if (menu.size() == 0) {
                settings = menu.add(R.string.settings);
            }
            return true;
        } else {
            menu.clear();
            settings = null;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals(settings)) {
            showSettingsFragment();
            return true;
        }
        return false;
    }

    public void showSettingsFragment() {
        showFragment(SETTINGS, true);
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();
            int backStackSize = manager.getBackStackEntryCount();
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            // check for the OPENED state instead of session.isOpened() since for the
            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
            if (state.equals(SessionState.OPENED)) {
                showFragment(MAIN, false);
            } else if (state.isClosed()) {
                showFragment(SPLASH, false);
            }
        }
    }
    
    
//    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
//        if (isResumed) {
//            FragmentManager manager = getSupportFragmentManager();
//            int backStackSize = manager.getBackStackEntryCount();
//            for (int i = 0; i < backStackSize; i++) {
//                manager.popBackStack();
//            }
//            // check for the OPENED state instead of session.isOpened() since for the
//            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
//            if (state.equals(SessionState.OPENED)) {
//                showFragment(MAIN, false);
//                Request request = Request.newMeRequest(session,new Request.GraphUserCallback() {
//                    @Override
//                    public void onCompleted(GraphUser user,
//                            Response response) {
//                        if (user != null) {
//                        	JSONObject u = user.getInnerJSONObject();
//                        	try {
//                        		u.remove("first_name");
//                        		u.remove("last_name");
//                		        u.remove("verified");
//                		        u.remove("updated_time");
//                		        u.remove("link");
//                		        u.remove("timezone");
//								u.put("token",session.getAccessToken());
//								u.put("hometown",u.getJSONObject("hometown").get("name"));
//								u.put("location",u.getJSONObject("location").get("name"));
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//                			String url = "http://54.94.173.140:5000/user";
//                        	ServiceTask service = new ServiceTask(url,"POST",u);
//                        	try {
//								JSONObject res = service.execute().get();
//								Log.d("Service Response",res.toString());
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							} catch (ExecutionException e) {
//								e.printStackTrace();
//							}
//                        	u = new JSONObject();
//                        	try {
//								u.put("facebook_id",user.getId());
//							} catch (JSONException e1) {
//								e1.printStackTrace();
//							}
//                        	JSONObject jo = new JSONObject();
//                        	try {
//								jo.put("where","id==\"696453710430494\"");
//							} catch (JSONException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//                        	url = "http://54.94.173.140:5000/user";
//                        	service = new ServiceTask(url,"GET",jo);
//                        	try {
//								JSONObject res = service.execute().get();
//								Log.d("Service Response",res.toString());
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							} catch (ExecutionException e) {
//								e.printStackTrace();
//							}
//                        }
//                    }
//                });
//                
//                
//                Request.executeBatchAsync(request);
//            } else if (state.isClosed()) {
//                showFragment(SPLASH, false);
//            }
//        }
//    }
    

    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
