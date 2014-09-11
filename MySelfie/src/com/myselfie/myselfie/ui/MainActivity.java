package com.myselfie.myselfie.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

	ActionBar mActionBar;
	ViewPager mViewPager;
	Tab tab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

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
		};

		// Create first Tab
		tab = mActionBar.newTab().setText("Me").setTabListener(tabListener);
		mActionBar.addTab(tab);

		// Create second Tab
		tab = mActionBar.newTab().setText("Friends")
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

		// Create third Tab
		tab = mActionBar.newTab().setText("Explore")
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

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
	      case R.id.add_item:
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
}
