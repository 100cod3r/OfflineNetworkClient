package com.queueing.util;

import org.apache.http.HttpResponse;

import com.queueing.callback.ResponseObserver;

public class MethodInstantiator {

	public void instatntiateCallbackMethod(Object  obj , HttpResponse response)
	{
		if(obj instanceof ResponseObserver)
		{
			((ResponseObserver)obj).onResponse(response);
		}
	}
}
