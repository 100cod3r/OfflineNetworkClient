package com.queueing.network;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.HttpResponse;

import android.content.Context;
import android.os.AsyncTask;

import com.queueing.callback.ResponseObserver;
import com.queueing.dao.RequestObject;
import com.queueing.queue.RequestQueue;

public class NetworkOperationTask extends AsyncTask<Void, Void, HttpResponse> {

	RequestObject requestObject;
	Context context;
	
	public NetworkOperationTask(Context context ,RequestObject requestObject) {
		super();
		this.requestObject = requestObject;
		this.context = context;
	}

	@Override
	protected HttpResponse doInBackground(Void... arg0) {
		return NetworkCommunicator.dispatchRequest(requestObject.getHttpRequest());

	}
	
	
	@Override
	protected void onPostExecute(HttpResponse response) {
		new RequestQueue().remove(context, requestObject);

		if(response != null)
		{
			Object obj = initObjectOfClass(requestObject.getCallbackClass(), response);
			if(obj instanceof ResponseObserver)
			{
				((ResponseObserver) obj).onResponse(response);
			}
		}
	}

	private Object initObjectOfClass(Class callbackClass , HttpResponse response)
	{
		try {

			Constructor constructor =  callbackClass.getConstructor(new Class[]{HttpResponse.class});
			
			Object instanceOfClass = constructor.newInstance(response);
			return instanceOfClass;

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
