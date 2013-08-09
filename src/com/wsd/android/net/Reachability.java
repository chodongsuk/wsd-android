package com.wsd.android.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class Reachability {
	public class AsyncReachability extends AsyncTask<Void, Void, Boolean> {
		private ReachabilityResponse response;
		
		public AsyncReachability(ReachabilityResponse response) {
			this.response = response;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			//  Some sort of connection is open, check if server is reachable
	        try {
	            URL url = new URL("http://www.google.com");
	            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	            urlc.setRequestProperty("User-Agent", "Android Application");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(30 * 1000); // Thirty seconds timeout in milliseconds
	            urlc.connect();
	            
	            Log.i("responseCode", urlc.getResponseCode() + "");
	            
	            if (urlc.getResponseCode() == 200 || urlc.getResponseCode() == 302) { // Good response
	                return true;
	            } else { // Anything else is unwanted
	                return false;
	            }
	        } catch (IOException e) {
	        	Log.i("error", e.getStackTrace().toString());
	            return false;
	        }
		}
		
		protected void onPostExecute(Boolean result) {
			if (result == true) response.isReachable();
			else response.isUnreachable();
		}
	}
	
	public Reachability(ReachabilityResponse response) {
		new AsyncReachability(response).execute();
	}
}
