package com.myselfie.myselfie.ui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.myselfie.myselfie.R;

public class MainActivity extends SherlockFragmentActivity implements TabListener {
	private static final String TAG = "MainActivity";
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private Tab tab;
	private boolean isResumed = false;
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);

		FragmentManager fm = getSupportFragmentManager();

		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				// Find the ViewPager Position
				mActionBar.setSelectedNavigationItem(position);
			}
		};

		mViewPager.setOnPageChangeListener(ViewPagerListener);
		ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
		mViewPager.setAdapter(viewpageradapter);

		// Create first Tab
		tab = mActionBar.newTab().setText("Me").setTabListener(this);
		mActionBar.addTab(tab);

		// Create second Tab
		tab = mActionBar.newTab().setText("Friends").setTabListener(this);
		mActionBar.addTab(tab);

		// Create third Tab
		tab = mActionBar.newTab().setText("Explore").setTabListener(this);
		mActionBar.addTab(tab);

		Session session = Session.getActiveSession();
		
		List<String> perm = session.getPermissions();
		Log.d(TAG,"perm size: "+perm.size());
		for(String s: perm){
			Log.d(TAG,"perm: "+s);
		}

		if (session != null && session.isOpened()) {
//			Bundle params = new Bundle();
//			params.putString("name", "My Test Album Name Here");
//			params.putString("message", "My Test Album Description Here");
//			Request request = new Request(session,
//					"me/albums", params, HttpMethod.POST,
//					new Request.Callback() {
//						@Override
//						public void onCompleted(Response response) {
//							Log.d(TAG,
//									"album created: "
//											+ response.getRawResponse());
//						}
//					});
//			RequestAsyncTask task = new RequestAsyncTask(request);
//			task.execute();
			
			Request request = new Request(session,
					"me", null, HttpMethod.GET,
					new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							Log.d(TAG,
									"me: "+ response.getRawResponse());
						}
					});
			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
			
			Request request1 = new Request(session,
					"me/albums", null, HttpMethod.GET,
					new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							try {
								JSONObject root = new JSONObject(response.toString());
								root = root.optJSONObject("data");
								if(root != null){
									
								}
							} catch (JSONException e) {
								Log.e(TAG,"",e);
							}
						}
					});
			RequestAsyncTask task1 = new RequestAsyncTask(request1);
			task1.execute();
		}else{
			Log.d(TAG,"Session not valid");
		}

	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.camera_item:
			// do s.th.
			return true;
		case R.id.about:
			// do s.th.
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		// Declare the number of ViewPager pages
		final int PAGE_COUNT = 3;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {

			// Open FragmentTab1.java
			case 0:
				MyselfFragment fragmentMyself = new MyselfFragment();
				return fragmentMyself;

				// Open FragmentTab2.java
			case 1:
				MyselfFragment fragmentMyself2 = new MyselfFragment();
				return fragmentMyself2;

				// Open FragmentTab3.java
			case 2:
				MyselfFragment fragmentMyself3 = new MyselfFragment();
				return fragmentMyself3;
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PAGE_COUNT;
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
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (isResumed) {
			// check for the OPENED state instead of session.isOpened() since
			// for the
			// OPENED_TOKEN_UPDATED state, the selection fragment should already
			// be showing.
			if (state.equals(SessionState.OPENED)) {

			} else if (state.isClosed()) {
				startActivity(new Intent(getBaseContext(), SplashActivity.class));
				finish();
			}
		}
	}
}
