package com.queueing.service;

import java.util.List;

import com.queueing.callback.NetworkConnectivityReceiver;
import com.queueing.contentproviderexample.DatabaseHelper;
import com.queueing.contentproviderexample.RequestBusQueueProvider;
import com.queueing.dao.RequestObject;
import com.queueing.network.NetworkOperationTask;
import com.queueing.queue.RequestQueue;

import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

public class QueueHandlerService extends Service {

	RequestQueue queue = new RequestQueue();
	NetworkConnectivityReceiver connectivityReceiver;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initQueueProcessing(this);
		 connectivityReceiver = new NetworkConnectivityReceiver();
		registerReceiver(connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
		return super.onStartCommand(intent, flags, startId);
	
	}
	
	private void initQueueProcessing(Context context) {
		List<RequestObject> pendingRequests = queue.peek(this);
		if(pendingRequests.size() >0)
		{
			new NetworkOperationTask(QueueHandlerService.this  , pendingRequests.get(0)).execute();
			//perform operation
		}		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(connectivityReceiver);
		super.onDestroy();
	}

}