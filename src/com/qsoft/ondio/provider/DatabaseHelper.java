package com.qsoft.ondio.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.qsoft.ondio.util.Common;

/**
 * User: anhnt
 * Date: 11/1/13
 * Time: 8:59 AM
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        createProfileTable(sqLiteDatabase);
        createFeedsTable(sqLiteDatabase);
    }

    private void createProfileTable(SQLiteDatabase sqLiteDatabase)
    {
        String createProfileTable =
                "CREATE TABLE " + Common.PROFILE_TABLE_NAME + " (" +
                        "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Common.PROFILE_ID + "TEXT," +
                        Common.PROFILE_DISPLAY_NAME + " TEXT, " +
                        Common.PROFILE_FULL_NAME + " TEXT, " +
                        Common.PROFILE_PHONE + " TEXT, " +
                        Common.PROFILE_BIRTHDAY + " TEXT, " +
                        Common.PROFILE_GENDER + " TEXT, " +
                        Common.PROFILE_COUNTRY + " TEXT, " +
                        Common.PROFILE_DESCRIPTION + " TEXT " +
                        ");";
        sqLiteDatabase.execSQL(createProfileTable);
    }

    private void createFeedsTable(SQLiteDatabase sqLiteDatabase)
    {
        String createFeedTable =
                "CREATE TABLE " + Common.FEED_TABLE_NAME + " (" +
                        Common.FEED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Common.FEED_TITLE + " TEXT, " +
                        Common.FEED_USER_ID + " TEXT, " +
                        Common.FEED_THUMBNAIL + " TEXT, " +
                        Common.FEED_DESCRIPTION + " TEXT, " +
                        Common.FEED_SOUND_PATH + " TEXT, " +
                        Common.FEED_DURATION + " TEXT, " +
                        Common.FEED_PLAYED + " TEXT, " +
                        Common.FEED_CREATED_AT + " TEXT, " +
                        Common.FEED_UPDATED_AT + " TEXT, " +
                        Common.FEED_LIKES + " TEXT, " +
                        Common.FEED_VIEWED + " TEXT, " +
                        Common.FEED_COMMENTS + " TEXT, " +
                        Common.FEED_USERNAME + " TEXT, " +
                        Common.FEED_DISPLAY_NAME + " TEXT, " +
                        Common.FEED_AVATAR + " TEXT " +
                        ");";
        sqLiteDatabase.execSQL(createFeedTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv)
    {
        upgradeTable(sqLiteDatabase, Common.PROFILE_TABLE_NAME);
        upgradeTable(sqLiteDatabase, Common.FEED_TABLE_NAME);
    }

    public void upgradeTable(SQLiteDatabase sqLiteDatabase, String tableName)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName + ";");
        createFeedsTable(sqLiteDatabase);
    }
}
