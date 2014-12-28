package com.queueing.contentproviderexample;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class RequestBusQueueProvider extends ContentProvider {

	private SQLiteDatabase db;
	static final String PROVIDER_NAME = "com.requestbus.queue";
	public static final String URL = "content://" + PROVIDER_NAME + "/"+DatabaseHelper.TABLE_REQUEST;
	public static final Uri CONTENT_URI = Uri.parse(URL);

	static final String id = "_id";
	static final int allRequestURIcode = 1;
	static final int singleRequestURIcode = 2;

	static final UriMatcher uriMatcher;
	private static HashMap<String, String> values;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, DatabaseHelper.TABLE_REQUEST, allRequestURIcode);
		uriMatcher.addURI(PROVIDER_NAME, DatabaseHelper.TABLE_REQUEST+"/*", singleRequestURIcode );
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case allRequestURIcode:
			break;
		case singleRequestURIcode:
            selection = selection + DatabaseHelper.KEY_ID+" = "+uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		count = db.delete(DatabaseHelper.TABLE_REQUEST, selection, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case allRequestURIcode:
			return "vnd.android.cursor.dir/"+DatabaseHelper.TABLE_REQUEST;
		case singleRequestURIcode:
			return "vnd.android.cursor.item/"+DatabaseHelper.TABLE_REQUEST;

		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = db.insert(DatabaseHelper.TABLE_REQUEST, "", values);
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to add a record into " + uri);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		if (db != null) {
			return true;
		}
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DatabaseHelper.TABLE_REQUEST);

		switch (uriMatcher.match(uri)) {
		case allRequestURIcode:
			qb.setProjectionMap(values);
			break;
		case singleRequestURIcode:
			qb.setProjectionMap(values);
			selection = selection + DatabaseHelper.KEY_ID+" = "+uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		if (TextUtils.isEmpty(sortOrder)) {
			sortOrder = DatabaseHelper.KEY_ID+" ASC";
		}
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case allRequestURIcode:
			break;
		case singleRequestURIcode:
            selection = selection + DatabaseHelper.KEY_ID+" = "+uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		count = db.update(DatabaseHelper.TABLE_REQUEST, values, selection, selectionArgs);
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}