package com.myselfie.myselfie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.myselfie.myselfie.R;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;

public class CameraFragment extends Fragment {
	private static final String TAG = "SettingsFragment";
	private SimpleFacebook mSimpleFacebook;
	private Button mButtonLogout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_camera, container, false);
		
		mSimpleFacebook = SimpleFacebook.getInstance(this.getActivity());
		mButtonLogout = (Button) view.findViewById(R.id.camera_button);
		
		mButtonLogout.setText("Take a picture");		
		mButtonLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				mSimpleFacebook.logout(new OnLogoutListener() {
				    @Override
				    public void onLogout() {
				        Log.i(TAG, "You are logged out");
				    }
		
				    @Override
					public void onThinking() {
						// TODO Auto-generated method stub
					}
		
					@Override
					public void onException(Throwable throwable) {
						// TODO Auto-generated method stub
					}
		
					@Override
					public void onFail(String reason) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);		
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
