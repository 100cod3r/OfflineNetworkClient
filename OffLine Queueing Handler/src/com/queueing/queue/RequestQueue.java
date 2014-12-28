package com.queueing.queue;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;

import android.content.Context;
import android.content.Intent;

import com.queueing.dao.RequestObject;
import com.queueing.service.QueueHandlerService;

public class RequestQueue {

	public void add(Context context , HttpRequestBase httpRequestBase, Class responseCallbackClass)
	{
		RequestQueueSource.add(context, new RequestObject(-1, httpRequestBase, httpRequestBase.getClass(), responseCallbackClass, System.currentTimeMillis()));
		context.startService(new Intent(context, QueueHandlerService.class));
	}
	
	public void add(Context context , RequestObject requestObj)
	{
		RequestQueueSource.add(context, requestObj);
	}
	
	public void remove(Context context , RequestObject requestObj)
	{
		RequestQueueSource.delete(context, requestObj);
	}
	
	/**Gets the first object of queue
	 * @return **/
	public List<RequestObject> peek(Context context )
	{
		return RequestQueueSource.peek(context);
	}
}
