package com.queueing.contentproviderexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	static final String DATABASE_NAME = "requestdb";
	public static final String TABLE_REQUEST = "REQUEST";
	static final int DATABASE_VERSION = 1;
	public static final String KEY_REQUEST_COLUMN ="REQUEST";
	public static final String KEY_REQUEST_CLASS_TYPE ="REQUEST_CLASS_TYPE";
	public static final String KEY_REQUEST_CALLBACK_CLASS ="CALLBACK_CLASS";
	public static final String KEY_ID = "_id";
	public static final String KEY_ADDED_DATE = "date";
	private static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_REQUEST
			+ " ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+  KEY_REQUEST_COLUMN+" VARCHAR, " 
			+  KEY_REQUEST_CLASS_TYPE+" VARCHAR, " 
			+ KEY_REQUEST_CALLBACK_CLASS+" VARCHAR, "
			+ KEY_ADDED_DATE+" INTEGER);";


	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
		onCreate(db);
	}
}