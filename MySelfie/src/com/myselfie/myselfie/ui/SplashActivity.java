package com.myselfie.myselfie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sromku.simple.fb.Permission.Type;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;

public class SplashActivity extends Activity {
	private static final String TAG = "SplashActivity";
	private SimpleFacebook mSimpleFacebook;
	private Button mButtonLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		mSimpleFacebook = SimpleFacebook.getInstance(this);

		mButtonLogin = (Button) findViewById(R.id.login_button);

		mButtonLogin.setText("Login via Facebook");		
		mButtonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSimpleFacebook.login(new OnLoginListener() {
					@Override
					public void onFail(String arg0) {
						Log.w(TAG,
								String.format("You didn't accept %s permissions", arg0));
					}

					@Override
					public void onException(Throwable arg0) {
						Log.e(TAG, "excemtion", arg0);
					}

					@Override
					public void onThinking() {
						Log.i(TAG, "User thinking");
					}

					@Override
					public void onNotAcceptingPermissions(Type arg0) {
						Log.w(TAG,
								String.format("You didn't accept %s permissions",
										arg0.name()));
					}

					@Override
					public void onLogin() {
						Log.i(TAG, "Logged in");
										
					    startActivity(new Intent(getBaseContext(), MainActivity.class));
					    finish();
					}
				});
			}
		});


	}

}