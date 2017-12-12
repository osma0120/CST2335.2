package com.example.mahad.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mahad on 2017-11-30.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    protected static final String CLASS_NAME = "ChatDataBaseHelper";

    public static final String DATABASE_NAME= "Messages.db";
    public static final int VERSION_NUM = 12;
    public static final String TABLE_NAME = "MyTable";
    public static final String KEY_ID="_id ";
    public static final String KEY_MESSAGE = "MESSAGE";




    public ChatDatabaseHelper(Context context ) {
        super(context, DATABASE_NAME , null, VERSION_NUM);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MESSAGE  +  " STRING );" );





}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(CLASS_NAME, " CALLING onCreate");
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion + newVersion" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(CLASS_NAME, " CALLING onCreate");
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion + newVersion" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
