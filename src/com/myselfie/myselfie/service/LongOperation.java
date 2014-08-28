package com.myselfie.myselfie.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.AsyncTask;

public class LongOperation extends AsyncTask<String, Void, Void> {

	// Required initialization

	private final HttpClient Client = new DefaultHttpClient();
	private String Content;
	private String Error = null;
	String data = "";
	int sizeData = 0;

	protected void onPreExecute() {

	}

	// Call after onPreExecute method
	protected Void doInBackground(String... urls) {

		BufferedReader reader = null;

		// Send data
		try {

			// Defined URL where to send data
			URL url = new URL(urls[0]);

			// Send POST data request

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the server response

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line + "");
			}

			// Append Server Response To Content String
			Content = sb.toString();
		} catch (Exception ex) {
			Error = ex.getMessage();
		} finally {
			try {
				reader.close();
			}
			catch (Exception ex) {
			}
		}
		return null;
	}

	protected void onPostExecute(Void unused) {
        if (Error != null) {
              
        } else {
             
            String OutputData = "";
            JSONObject jsonResponse;
                   
            try {
                 jsonResponse = new JSONObject(Content);                  
                   
             } catch (JSONException e) {
       
                 e.printStackTrace();
             }

              
         }
    }
}
