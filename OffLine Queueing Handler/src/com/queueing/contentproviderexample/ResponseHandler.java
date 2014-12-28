package com.queueing.contentproviderexample;

import org.apache.http.HttpResponse;

import android.util.Log;

import com.queueing.callback.ResponseObserver;

public class ResponseHandler implements ResponseObserver{

	@Override
	public void onResponse(HttpResponse response) {
		Log.d("","response received"+response);
	}

}
