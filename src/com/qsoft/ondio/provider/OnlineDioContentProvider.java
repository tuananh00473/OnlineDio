package com.qsoft.ondio.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.qsoft.ondio.util.Common;

import java.util.HashMap;

/**
 * Simple content provider that demonstrates the basics of creating a content
 * provider that stores basic video meta-data.
 */
public class OnlineDioContentProvider extends ContentProvider
{
    public static final int FEEDS = 1;
    public static final int FEED_ID = 2;
    public static final int PROFILES = 3;
    public static final int PROFILE_ID = 4;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase mDb;

    private static HashMap<String, String> PROFILE_PROJECTION_MAP;

    private static UriMatcher sUriMatcher;

    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Common.PROVIDER_NAME, Common.FEED_TABLE_NAME, FEEDS);
        sUriMatcher.addURI(Common.PROVIDER_NAME, Common.FEED_TABLE_NAME + "/#", FEED_ID);
        sUriMatcher.addURI(Common.PROVIDER_NAME, Common.PROFILE_TABLE_NAME, PROFILES);
        sUriMatcher.addURI(Common.PROVIDER_NAME, Common.PROFILE_TABLE_NAME + "/#", PROFILE_ID);
    }


    @Override
    public boolean onCreate()
    {
        databaseHelper = new DatabaseHelper(getContext(), Common.DATABASE_NAME, null);
        mDb = databaseHelper.getWritableDatabase();

        return (mDb == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Common.PROFILE_TABLE_NAME);

        switch (sUriMatcher.match(uri))
        {
            case PROFILES:
                qb.setProjectionMap(PROFILE_PROJECTION_MAP);
                break;
            case PROFILE_ID:
                qb.appendWhere(Common.PROFILE_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "")
        {
            sortOrder = Common.PROFILE_FULL_NAME;
        }
        Cursor c = qb.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri)
    {
        switch (sUriMatcher.match(uri))
        {
            case PROFILES:
                return "vnd.android.cursor.dir/vnd.onldio.profiles";
            case PROFILE_ID:
                return "vnd.android.cursor.item/vnd.onldio.profiles";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long rowID = mDb.insert(Common.PROFILE_TABLE_NAME, "", values);
        /**
         * If record is added successfully
         */
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(Common.CONTENT_URI_PROFILE, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int count = 0;

        switch (sUriMatcher.match(uri))
        {
            case PROFILES:
                count = mDb.delete(Common.PROFILE_TABLE_NAME, selection, selectionArgs);
                break;
            case PROFILE_ID:
                String id = uri.getPathSegments().get(1);
                count = mDb.delete(Common.PROFILE_TABLE_NAME, Common.PROFILE_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int count = 0;

        switch (sUriMatcher.match(uri))
        {
            case PROFILES:
                count = mDb.update(Common.PROFILE_TABLE_NAME, values, selection, selectionArgs);
                break;
            case PROFILE_ID:
                count = mDb.update(Common.PROFILE_TABLE_NAME, values, Common.PROFILE_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
