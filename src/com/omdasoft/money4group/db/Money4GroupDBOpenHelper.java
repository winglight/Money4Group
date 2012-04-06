package com.omdasoft.money4group.db;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Money4GroupDBOpenHelper extends SQLiteOpenHelper {
	
	public  static final int DATABASE_VERSION = 1;
	
	//ACTIVITY TABLE
    public  static final String ACTIVITY_TABLE_NAME = "activity";
	public  static final String ACT_ID = "ACT_ID";
	public  static final String ACT_NAME = "ACT_NAME";
	public  static final String ACT_TYPE = "ACT_TYPE";
	public  static final String START_AT = "START_AT";
	public  static final String END_AT = "END_AT";
	
	//USER TABLE
	public  static final String USER_TABLE_NAME = "user";
	public  static final String USER_NAME = "USER_NAME";
	public  static final String CONTACT = "CONTACT";

	//RECORD TABLE
	public  static final String RECORD_TABLE_NAME = "record";
	public  static final String RECORD_DESC = "RECORD_DESC";
	public  static final String RECORD_AMOUNT = "record";
	public  static final String SPENT_ON = "SPENT_ON";
	public  static final String PAID_BY = "PAID_BY";
	public  static final String COSUMED_BY = "COSUMED_BY";
	public  static final String TAG = "TAG";
		
	//common column
	public  static final String CREATED_BY = "CREATED_BY";
	public  static final String CREATED_AT = "CREATED_AT";
	public  static final String MODIFIED_BY = "MODIFIED_BY";
	public  static final String MODIFIED_AT = "MODIFIED_AT";
	
    public  static final String ACTIVITY_TABLE_CREATE =
                "CREATE TABLE " + ACTIVITY_TABLE_NAME + " (" +
                "_id integer primary key autoincrement," +
                ACT_NAME + " TEXT, " +
                ACT_TYPE + " INTEGER, " +
                START_AT + " TEXT, " +
                END_AT + " TEXT, " +
                CREATED_BY + " TEXT, " +
                CREATED_AT + " INTEGER, " +
                MODIFIED_BY + " TEXT, " +
                MODIFIED_AT + " INTEGER " + 
                ");";
    
    public  static final String USER_TABLE_CREATE =
            "CREATE TABLE " + ACTIVITY_TABLE_NAME + " (" +
            "_id integer primary key autoincrement," +
            USER_NAME + " TEXT, " +
            CONTACT + " TEXT, " +
            CREATED_BY + " TEXT, " +
            CREATED_AT + " INTEGER, " +
            MODIFIED_BY + " TEXT, " +
            MODIFIED_AT + " INTEGER " + 
            ");";
    
    public  static final String RECORD_TABLE_CREATE =
            "CREATE TABLE " + ACTIVITY_TABLE_NAME + " (" +
            "_id integer primary key autoincrement," +
            ACT_ID + " integer, " +
            RECORD_DESC + " TEXT, " +
            RECORD_AMOUNT + " REAL, " +
            SPENT_ON + " TEXT, " +
            PAID_BY + " TEXT, " +
            COSUMED_BY + " TEXT, " +
            TAG + " TEXT, " +
            CREATED_BY + " TEXT, " +
            CREATED_AT + " INTEGER, " +
            MODIFIED_BY + " TEXT, " +
            MODIFIED_AT + " INTEGER " + 
            ");";
	public static final String DATABASE_NAME = "moneygroup";

    Money4GroupDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	Date dt = new Date();
        db.execSQL(ACTIVITY_TABLE_CREATE);
        db.execSQL("INSERT INTO " + ACTIVITY_TABLE_CREATE + " VALUE(1, 'Default Activity', '" + dt + "', " + dt + "', " + dt.getTime() + ", " + dt.getTime() + ", " + dt.getTime() + ", " + dt.getTime() + ");");
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(RECORD_TABLE_CREATE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w("Constants", "Upgrading database, which will destroy all old	data");
				db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE_CREATE);
				db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_CREATE);
				db.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE_CREATE);
				onCreate(db);
		
	}

}
