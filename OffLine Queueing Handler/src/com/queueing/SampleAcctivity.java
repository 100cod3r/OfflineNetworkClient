package com.queueing;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.contentproviderexample.R;
import com.example.contentproviderexample.R.layout;
import com.example.contentproviderexample.R.menu;
import com.queueing.contentproviderexample.ResponseHandler;
import com.queueing.network.NetworkCommunicator;
import com.queueing.queue.RequestQueue;

public class SampleAcctivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		new RequestQueue().add(this, NetworkCommunicator.prepareRequest(), ResponseHandler.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClickAddName(View view) {
		
	}


	  
	  
}

