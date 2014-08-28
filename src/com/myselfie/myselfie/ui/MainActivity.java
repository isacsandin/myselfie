package com.myselfie.myselfie.ui;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import com.myselfie.myselfie.R;
import com.myselfie.myselfie.R.drawable;
import com.myselfie.myselfie.R.id;
import com.myselfie.myselfie.R.layout;
import com.myselfie.myselfie.R.string;
import com.myselfie.utils.Utils;

public class MainActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener,OnClickListener {

	private TabHost mTabHost;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;

	private class TabInfo {
		private String _tag;
		private int _drawableId;
		@SuppressWarnings("rawtypes")
		private Class _class;
		private Bundle _args;
		private Fragment _fragment;

		@SuppressWarnings("rawtypes")
		TabInfo(int labelID, int drawableId, Class cl, Bundle args) {
			this._tag = "tab" + labelID;
			this._drawableId = drawableId;
			this._class = cl;
			this._args = args;
		}
		
		public int get_drawableId() {
			return _drawableId;
		}

		@SuppressWarnings("rawtypes")
		public Class get_class() {
			return _class;
		}

		public Fragment get_fragment() {
			return _fragment;
		}

		public String get_tag() {
			return _tag;
		}

	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Step 1: Inflate layout
		setContentView(R.layout.activity_main);
		ViewGroup vg = (ViewGroup)findViewById(R.id.main_root);
		
		// Step 2: Setup TabHost
		initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			// set the tab as per the saved state
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		ImageButton btnCompose = (ImageButton) findViewById(R.id.btn_compose);
		btnCompose.setOnClickListener(this);
		
		Utils.setFontAllView(vg);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
																// selected
		super.onSaveInstanceState(outState);
	}

	/**
	 * Step 2: Setup TabHost
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		addTab(R.string.lbl_master_detail, R.drawable.ico_star,
				MasterDetailActivity.class, args);
		addTab(R.string.lbl_swipe, R.drawable.ico_list,
				SwipeActivity.class,args);
		addTab(R.string.lbl_check_list, R.drawable.ico_check,
				MyCameraActivity.class, args);
		addTab(R.string.lbl_facebook, R.drawable.ico_settings,
				MainFragment.class, args);

		// Default to first tab
		this.onTabChanged("tab" + R.string.lbl_master_detail);
		//
		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	private void addTab(int labelID, int drawableId, Class cl, Bundle args) {

		TabInfo tabInfo = null;
		tabInfo = new TabInfo(labelID, drawableId, cl, args);
		this.mapTabInfo.put(tabInfo.get_tag(), tabInfo);

		TabHost.TabSpec spec = mTabHost.newTabSpec(tabInfo._tag);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator,
				(ViewGroup) findViewById(android.R.id.tabs), false);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(tabInfo.get_drawableId());

		spec.setIndicator(tabIndicator);

		// Attach a Tab view factory to the spec
		spec.setContent(this.new TabFactory(this));
		String tag = spec.getTag();

		// Check to see if we already have a fragment for this tab, probably
		// from a previously saved state. If so, deactivate it, because our
		// initial state is that a tab isn't shown.
		tabInfo._fragment = this.getSupportFragmentManager().findFragmentByTag(
				tag);
		if (tabInfo._fragment != null && !tabInfo._fragment.isDetached()) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			ft.detach(tabInfo._fragment);
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}

		mTabHost.addTab(spec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		TabInfo newTab = this.mapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			if (mLastTab != null) {
				if (mLastTab._fragment != null) {
					ft.detach(mLastTab._fragment);
				}
			}
			if (newTab != null) {
				if (newTab.get_fragment() == null) {
					newTab._fragment = Fragment.instantiate(this, newTab
							.get_class().getName(), newTab._args);
					ft.add(R.id.realtabcontent, newTab.get_fragment(),
							newTab._tag);
				} else {
					ft.attach(newTab.get_fragment());
				}
			}

			mLastTab = newTab;
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_compose) {
			//Intent i = new Intent(this.getApplicationContext(),SubActivity.class);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
			startActivity(cameraIntent);

		}

	}
}
