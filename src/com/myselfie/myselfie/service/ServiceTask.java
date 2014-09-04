package com.myselfie.myselfie.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ServiceTask extends AsyncTask<String, String, JSONObject> {
	JSONObject postparams = null;
	String url = null;
	String method = null;
	InputStream is;
	JSONObject jObj = null;
	String json = "";

	public ServiceTask(String url, String method, JSONObject params) {
		this.url = url;
		if (params == null) {
			this.postparams = new JSONObject();
		} else {
			this.postparams = params;
		}
		this.method = method;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			if (method.equals("POST")) {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");
				StringEntity se = null;
				try {
					se = new StringEntity(postparams.toString());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				se.setContentEncoding("utf-8");
				se.setContentType("application/json");
				httpPost.setEntity(se);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} else if (method == "GET") {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String [] names = JSONObject.getNames(postparams);
				List<NameValuePair> par = new ArrayList<NameValuePair>();
				for(int i = 0; i<names.length; i++){
					par.add(new BasicNameValuePair(names[i],postparams.optString(names[i])));
				}
				String paramString = URLEncodedUtils.format(par, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("Accept", "application/json");

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return jObj;

	}
}
