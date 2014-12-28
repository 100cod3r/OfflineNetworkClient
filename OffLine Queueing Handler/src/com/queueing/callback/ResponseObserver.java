package com.queueing.callback;

import org.apache.http.HttpResponse;

public interface ResponseObserver {

	public void onResponse(HttpResponse response);
}
