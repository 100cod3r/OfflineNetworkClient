package com.queueing.network;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.entity.EntitySerializer;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.queueing.util.Constants;

public class NetworkCommunicator {


	public static final String API_KEY ="AIzaSyCduFhwIh69SEu0fvOyjNCVLnl4zMxEmZ0";
	public static final String API_SERVER_KEY ="AIzaSyDdXeUjX4osh3QM5lUXbThYsMgUoIKsUYw";
	private static final String TAG = NetworkCommunicator.class.getName();

	
	public static HttpGet prepareRequest()
	{
		String googlePlaces="https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
			+ "abc"
			+ "&types=geocode&language=en&sensor=true&key="
			+ API_SERVER_KEY;

		return new HttpGet(googlePlaces);
	
	}
	
    public static HttpResponse dispatchRequest(HttpRequestBase httpRequest)
    {
         HttpParams httpParameters = new BasicHttpParams();
         HttpConnectionParams.setConnectionTimeout(httpParameters, Constants.TIMEOUT_CONNECTION);
         HttpConnectionParams.setSoTimeout(httpParameters, Constants.TIMEOUT_SOCKET);
         HttpClient httpClient = new DefaultHttpClient(httpParameters);
         
         HttpResponse responseObj;
		try {
        	 
             responseObj = httpClient.execute(httpRequest);
             String result = EntityUtils.toString(responseObj.getEntity());
             
             Log.d(TAG ,"response="+result);
/*
             if (responseObj.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {*/
				return responseObj;
 			/*}*/

 			
 			
         } 
         catch (Exception e) {
         	Log.e(TAG, e.toString(), e);
            
             httpRequest.abort();
        	 return null;
         }

    }
    

           
	
}
