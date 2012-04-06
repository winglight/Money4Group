package com.omdasoft.money4group.db;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class UserDAO {

	private SQLiteDatabase db;
	private final Context context;

	private static UserDAO instance;
	private Money4GroupDBOpenHelper sdbHelper;
	
	public static Integer lock_db=0;
	
	private UserDAO(Context c) {
		this.context = c;
		this.sdbHelper = new Money4GroupDBOpenHelper(this.context);
	}

	public void close() {
		synchronized(lock_db){
			if(lock_db == 1){
			lock_db--;
		db.close();
			}
		}
	}

	public void open() throws SQLiteException {
		try {
			synchronized(lock_db){
				if(lock_db == 0){
				lock_db++;
			db = sdbHelper.getWritableDatabase();
			
			db.setLockingEnabled(true);
				}
			}
		} catch (SQLiteException ex) {
			Log.v("Open database exception caught", ex.getMessage());
			db = sdbHelper.getReadableDatabase();
		}
	}

	public static UserDAO getInstance(Context c) {
		if (instance == null) {
			instance = new UserDAO(c);
		}
		return instance;
	}

	public Cursor getAllActivity() {
		Cursor c = db.query(Money4GroupDBOpenHelper.ACTIVITY_TABLE_NAME, null,null, null,
				 null, null, Money4GroupDBOpenHelper.CREATED_AT + " desc");
				
		return c;
	}
	
	public int getMaxId() {
		return 0;
	}

	public long insert(UserModel am) {

		try{
			ContentValues newActValue = new ContentValues();
			newActValue.put(Money4GroupDBOpenHelper.USER_NAME, am.getUserName());
			newActValue.put(Money4GroupDBOpenHelper.CONTACT, am.getContact());
			newActValue.put(Money4GroupDBOpenHelper.CREATED_AT, am.getCreatedAt());
			newActValue.put(Money4GroupDBOpenHelper.CREATED_BY, am.getCreatedBy());
			newActValue.put(Money4GroupDBOpenHelper.MODIFIED_AT, am.getModifiedAt());
			newActValue.put(Money4GroupDBOpenHelper.MODIFIED_BY, am.getModifiedBy());
			return db.insert(Money4GroupDBOpenHelper.ACTIVITY_TABLE_NAME, null, newActValue);
			} catch(SQLiteException ex) {
				Log.v("Insert into database exception caught",
						ex.getMessage());
				return -1;
			}
	}
	
	public long update(UserModel am) {

		try{
			synchronized(lock_db){
				lock_db = lock_db -2;
			ContentValues newActValue = new ContentValues();
			newActValue.put(Money4GroupDBOpenHelper.USER_NAME, am.getUserName());
			newActValue.put(Money4GroupDBOpenHelper.CONTACT, am.getContact());
			newActValue.put(Money4GroupDBOpenHelper.CREATED_AT, am.getCreatedAt());
			newActValue.put(Money4GroupDBOpenHelper.CREATED_BY, am.getCreatedBy());
			newActValue.put(Money4GroupDBOpenHelper.MODIFIED_AT, am.getModifiedAt());
			newActValue.put(Money4GroupDBOpenHelper.MODIFIED_BY, am.getModifiedBy());
			int res = db.update(Money4GroupDBOpenHelper.ACTIVITY_TABLE_NAME, newActValue, "_id =" + am.getId(), null);
			lock_db = lock_db +2;
			return res;
			}
			} catch(SQLiteException ex) {
				Log.v("update database exception caught",
						ex.getMessage());
				return -1;
			}
	}
	
	public boolean updateOrInsert(UserModel rm) {
		long res = update(rm);
		if(res > 0){
			return true;
		}else{
			//insert
//			rm.setNameEn(translate(rm.getName()));
			insert(rm);
			return false;
		}
	}
	
	
	
	public long delete(long id) {
		try{
			return db.delete(Money4GroupDBOpenHelper.ACTIVITY_TABLE_NAME, "_id=" + id, null);
			
			
			} catch(SQLiteException ex) {
				Log.v("delete database exception caught",
						ex.getMessage());
				return -1;
			}
	}
	
}
