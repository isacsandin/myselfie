/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myselfie.myselfie.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.myselfie.myselfie.R;
import com.sromku.simple.fb.Permission.Type;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;

public class SplashFragment extends Fragment {
	private static final String TAG = "SplashFragment";
	private SimpleFacebook mSimpleFacebook;
	private Button mButtonLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_splash, container, false);

		mSimpleFacebook = SimpleFacebook.getInstance(this.getActivity());

		mButtonLogin = (Button) view.findViewById(R.id.login_button);
		
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
					}
				});
			}
		});
		return view;
	}

}
