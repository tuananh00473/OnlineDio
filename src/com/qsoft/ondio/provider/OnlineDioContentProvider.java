package com.qsoft.ondio.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.finchframework.finch.rest.FileHandlerFactory;
import com.finchframework.finch.rest.RESTfulContentProvider;
import com.finchframework.finch.rest.ResponseHandler;

import java.io.File;

/**
 * Simple content provider that demonstrates the basics of creating a content
 * provider that stores basic video meta-data.
 */
public class OnlineDioContentProvider extends RESTfulContentProvider
{
    public static final String PROVIDER_NAME = "com.qsoft.ondio.feed";
    public static final String URL_CONTENT = "content://" + PROVIDER_NAME + "/feed";

    public static final String DATABASE_NAME = "localdata.db";
    public static int DATABASE_VERSION = 2;

    public static final int FEEDS = 1;
    public static final int FEED_ID = 2;

    public static final String FEED_TABLE_NAME = "feed";
    private static final String FINCH_FEED_FILE_CACHE = "finch_feed_file_cache";

    private DatabaseHelper mOpenHelper;
    private SQLiteDatabase mDb;

    private static UriMatcher sUriMatcher;

    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(PROVIDER_NAME, "feeds", FEEDS);
        sUriMatcher.addURI(PROVIDER_NAME, "feeds/#", FEED_ID);
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv, SQLiteDatabase db)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SQLiteDatabase getDatabase()
    {
        return mDb;
    }

    @Override
    protected ResponseHandler newResponseHandler(String requestTag)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onCreate()
    {
        FileHandlerFactory fileHandlerFactory =
                new FileHandlerFactory(new File(getContext().getFilesDir(),
                        FINCH_FEED_FILE_CACHE));
        setFileHandlerFactory(fileHandlerFactory);

        mOpenHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null);
        mDb = mOpenHelper.getWritableDatabase();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getType(Uri uri)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int delete(Uri uri, String s, String[] strings)
    {
        int match = sUriMatcher.match(uri);
        int affected = -1000000;     // feck
//
//        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//        switch (match) {
//            case VIDEO_ID:
//                long videoId = ContentUris.parseId(uri);
//                affected = db.delete(VIDEOS_TABLE_NAME,
//                        BaseColumns._ID + "=" + videoId
//                                + (!TextUtils.isEmpty(where) ?
//                                " AND (" + where + ')' : ""),
//                        whereArgs);
//                getContext().getContentResolver().notifyChange(uri, null);
//
//                break;
//            default:
//                throw new IllegalArgumentException("unknown video element: " +
//                        uri);
//        }
//
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
        {
            super(context, name, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            createTable(sqLiteDatabase);
        }

        private void createTable(SQLiteDatabase sqLiteDatabase)
        {
            String createvideoTable =
                    "CREATE TABLE " + FEED_TABLE_NAME + " (" +
                            FinchIFeed.Feed.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            FinchIFeed.Feed.TITLE + " TEXT, " +
                            FinchIFeed.Feed.USER_ID + " TEXT, " +
                            FinchIFeed.Feed.THUMBNAIL + " TEXT, " +
                            FinchIFeed.Feed.DESCRIPTION + " TEXT, " +
                            FinchIFeed.Feed.SOUND_PATH + " TEXT, " +
                            FinchIFeed.Feed.DURATION + " TEXT, " +
                            FinchIFeed.Feed.PLAYED + " TEXT, " +
                            FinchIFeed.Feed.CREATED_AT + " TEXT, " +
                            FinchIFeed.Feed.UPDATE_AT + " TEXT, " +
                            FinchIFeed.Feed.LIKES + " TEXT, " +
                            FinchIFeed.Feed.VIEWED + " TEXT, " +
                            FinchIFeed.Feed.COMMENTS + " TEXT, " +
                            FinchIFeed.Feed.USERNAME + " TEXT, " +
                            FinchIFeed.Feed.DISPLAY_NAME + " TEXT, " +
                            FinchIFeed.Feed.AVATAR + " TEXT " +
                            ");";
            sqLiteDatabase.execSQL(createvideoTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv,
                              int newv)
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                    FEED_TABLE_NAME + ";");
            createTable(sqLiteDatabase);
        }
    }
}
