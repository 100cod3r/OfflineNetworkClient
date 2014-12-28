package com.queueing.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.http.client.methods.HttpRequestBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.queueing.contentproviderexample.DatabaseHelper;
import com.queueing.contentproviderexample.RequestBusQueueProvider;
import com.queueing.dao.RequestObject;

public class RequestQueueSource {

	private static final String TAG = RequestQueueSource.class.getName();

	public static void add(Context context ,RequestObject requestObj)
	{
		Gson gson = new GsonBuilder().
        	setExclusionStrategies(new ParseExclusion()).
        	create();
        
        String serializedJson = (gson.toJson(requestObj.getHttpRequest()));
        Log.d(TAG , "response="+serializedJson);
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.KEY_REQUEST_COLUMN, serializedJson);
		contentValues.put(DatabaseHelper.KEY_REQUEST_CLASS_TYPE, requestObj.getHttpRequest().getClass().getName());
		contentValues.put(DatabaseHelper.KEY_REQUEST_CALLBACK_CLASS, requestObj.getCallbackClass().getName());
		contentValues.put(DatabaseHelper.KEY_ADDED_DATE, System.currentTimeMillis());
		
		context.getContentResolver().insert( RequestBusQueueProvider.CONTENT_URI, contentValues);
	}
	
	public static void delete(Context context ,RequestObject requestObj)
	{
		context.getContentResolver().delete( Uri.parse(RequestBusQueueProvider.URL+"/"+requestObj.getId()), null, null);
	}
	
	/**gets topmost element**/
	public static List<RequestObject> peek(Context context)
	{
		/***Get top most element
		 * select *
		from Table
		order by _id desc limit 1***/
		String sortOrder = DatabaseHelper.KEY_ID+" DESC LIMIT 1";
		return queryResult(context, sortOrder);
	}
	
	/**gets topmost element**/
	public static List<RequestObject> getAll(Context context)
	{
		/***Get top most element
		 * select *
		from Table
		order by _id desc limit 1***/
		String sortOrder = DatabaseHelper.KEY_ID+" DESC";
		return queryResult(context, sortOrder);
		
	}
	
	private static List<RequestObject> queryResult(Context context , String sortOrder)
	{
		Cursor mCursor = context.getContentResolver().query(RequestBusQueueProvider.CONTENT_URI, null, null, new String[]{}, sortOrder);
		return cursorToObject(mCursor);
	
	}
	
	private static List<RequestObject> cursorToObject(Cursor cursor)
	{
		List<RequestObject> requestObjects = new ArrayList<RequestObject>();

		try{
		while(cursor.moveToNext()) {
		      int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
		      long date = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ADDED_DATE));
		      String requestString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_REQUEST_COLUMN));
		      String requestClassTypeString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_REQUEST_CLASS_TYPE));
		      String callbackClassString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_REQUEST_CALLBACK_CLASS));
		      
		      Class requestClassType;
				requestClassType = Class.forName(requestClassTypeString);
			
		      Class callbackClass = Class.forName(callbackClassString);
		    //deserialize
		      Gson gson = new GsonBuilder().
	        	setExclusionStrategies(new ParseExclusion()).
	        	create();
		      HttpRequestBase httpRequest = (HttpRequestBase) gson.fromJson(requestString, requestClassType);
	        
		      
		      requestObjects.add(new RequestObject(id, httpRequest, requestClassType, callbackClass, date));	
			}	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return requestObjects;
	}
	
	private static class ParseExclusion implements ExclusionStrategy {

		    public boolean shouldSkipClass(Class<?> arg0) {
		        return false;
		    }

		    public boolean shouldSkipField(FieldAttributes f) {
		        return (f.getDeclaredClass() == Lock.class);
		    }

		}
}
