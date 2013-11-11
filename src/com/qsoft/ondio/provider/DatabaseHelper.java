package com.qsoft.ondio.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.qsoft.ondio.util.Constants;

/**
 * User: AnhNT
 * Date: 11/1/13
 * Time: 8:59 AM
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        createTables(sqLiteDatabase);
        Log.d(TAG, "Created table.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv)
    {
        upgradeTables(sqLiteDatabase);
        createTables(sqLiteDatabase);
        Log.d(TAG, "Upgraded table.");
    }

    private void createTables(SQLiteDatabase sqLiteDatabase)
    {
        createUsersTable(sqLiteDatabase);
        createFeedsTable(sqLiteDatabase);
        createProfileTable(sqLiteDatabase);
    }

    private void createProfileTable(SQLiteDatabase sqLiteDatabase)
    {
        String createProfileTable =
                "CREATE TABLE " + Constants.PROFILE_TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Constants.PROFILE_ID + " TEXT," +
                        Constants.PROFILE_AVATAR + " TEXT," +
                        Constants.PROFILE_COVER_IMAGE + " TEXT," +
                        Constants.PROFILE_DISPLAY_NAME + " TEXT, " +
                        Constants.PROFILE_FULL_NAME + " TEXT, " +
                        Constants.PROFILE_PHONE + " TEXT, " +
                        Constants.PROFILE_BIRTHDAY + " TEXT, " +
                        Constants.PROFILE_GENDER + " INTEGER, " +
                        Constants.PROFILE_COUNTRY + " INTEGER, " +
                        Constants.PROFILE_DESCRIPTION + " TEXT " +
                        ");";
        sqLiteDatabase.execSQL(createProfileTable);
    }

    private void createFeedsTable(SQLiteDatabase sqLiteDatabase)
    {
        String createFeedTable =
                "CREATE TABLE " + Constants.FEED_TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Constants.FEED_ID + " INTEGER, " +
                        Constants.FEED_USER_ID + " INTEGER, " +
                        Constants.FEED_ACCOUNT_ID + " INTEGER, " +
                        Constants.FEED_TITLE + " TEXT, " +
                        Constants.FEED_THUMBNAIL + " TEXT, " +
                        Constants.FEED_DESCRIPTION + " TEXT, " +
                        Constants.FEED_SOUND_PATH + " TEXT, " +
                        Constants.FEED_DURATION + " INTEGER, " +
                        Constants.FEED_PLAYED + " INTEGER, " +
                        Constants.FEED_CREATED_AT + " TEXT, " +
                        Constants.FEED_UPDATED_AT + " TEXT, " +
                        Constants.FEED_LIKES + " INTEGER, " +
                        Constants.FEED_VIEWED + " INTEGER, " +
                        Constants.FEED_COMMENTS + " INTEGER, " +
                        Constants.FEED_USERNAME + " TEXT, " +
                        Constants.FEED_DISPLAY_NAME + " TEXT, " +
                        Constants.FEED_AVATAR + " TEXT " +
                        ");";
        sqLiteDatabase.execSQL(createFeedTable);
    }

    private void createUsersTable(SQLiteDatabase sqLiteDatabase)
    {
        String createUserTable =
                "CREATE TABLE " + Constants.USER_TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Constants.USER_ID + " TEXT, " +
                        Constants.USER_ACCESS_TOKEN + " TEXT, " +
                        Constants.USER_CLIENT_ID + " TEXT, " +
                        Constants.USER_USER_ID + " TEXT, " +
                        Constants.USER_EXPIRES + " TEXT, " +
                        Constants.USER_SCOPE + " TEXT " +
                        ");";
        sqLiteDatabase.execSQL(createUserTable);
    }

    public void upgradeTables(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.USER_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.FEED_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.PROFILE_TABLE_NAME + ";");
    }
}
