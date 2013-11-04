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
import android.util.Log;
import com.qsoft.ondio.util.Constants;

import java.util.HashMap;

/**
 * User: AnhNT
 * Date: 10/30/13
 * Time: 8:53 AM
 */

public class OnlineDioContentProvider extends ContentProvider
{

    public static final int USERS = 1;
    public static final int USER_ID = 2;
    public static final int FEEDS = 3;
    public static final int FEED_ID = 4;
    public static final int PROFILES = 5;
    public static final int PROFILE_ID = 6;
    private static final String TAG = "OnlineDioContentProvider";


    private DatabaseHelper databaseHelper;
    private SQLiteDatabase mDb;

    private static HashMap<String, String> USER_PROJECTION_MAP;
    private static HashMap<String, String> FEED_PROJECTION_MAP;
    private static HashMap<String, String> PROFILE_PROJECTION_MAP;

    private static UriMatcher sUriMatcher;

    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.USER_TABLE_NAME, USERS);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.USER_TABLE_NAME + "/#", USER_ID);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.FEED_TABLE_NAME, FEEDS);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.FEED_TABLE_NAME + "/#", FEED_ID);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.PROFILE_TABLE_NAME, PROFILES);
        sUriMatcher.addURI(Constants.PROVIDER_NAME, Constants.PROFILE_TABLE_NAME + "/#", PROFILE_ID);
    }


    @Override
    public boolean onCreate()
    {
        databaseHelper = new DatabaseHelper(getContext(), Constants.DATABASE_NAME, null);
        mDb = databaseHelper.getWritableDatabase();

        return (mDb == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri))
        {
            case USERS:
                qb.setTables(Constants.USER_TABLE_NAME);
                qb.setProjectionMap(USER_PROJECTION_MAP);
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.USER_ID;
                }
                break;
            case USER_ID:
                qb.setTables(Constants.USER_TABLE_NAME);
                qb.appendWhere(Constants.USER_ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.USER_ID;
                }
                break;
            case FEEDS:
                qb.setTables(Constants.FEED_TABLE_NAME);
                qb.setProjectionMap(FEED_PROJECTION_MAP);
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.FEED_DISPLAY_NAME;
                }
                break;
            case FEED_ID:
                qb.setTables(Constants.FEED_TABLE_NAME);
                qb.appendWhere(Constants.FEED_ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.FEED_DISPLAY_NAME;
                }
                break;
            case PROFILES:
                qb.setTables(Constants.PROFILE_TABLE_NAME);
                qb.setProjectionMap(PROFILE_PROJECTION_MAP);
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.PROFILE_FULL_NAME;
                }
                break;
            case PROFILE_ID:
                qb.setTables(Constants.PROFILE_TABLE_NAME);
                qb.appendWhere(Constants.PROFILE_ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null || sortOrder == "")
                {
                    sortOrder = Constants.PROFILE_FULL_NAME;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
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
            case USERS:
                return "vnd.android.cursor.dir/vnd.onldio.users";
            case USER_ID:
                return "vnd.android.cursor.item/vnd.onldio.users";
            case FEEDS:
                return "vnd.android.cursor.dir/vnd.onldio.feeds";
            case FEED_ID:
                return "vnd.android.cursor.item/vnd.onldio.feeds";
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
        Uri _uri = null;
        int keySwitch = sUriMatcher.match(uri);
        Log.d(TAG, "keySwitch = " + keySwitch);
        switch (keySwitch)
        {
            case USERS:
                long rowUserId = mDb.insert(Constants.USER_TABLE_NAME, "", values);
                if (rowUserId > 0)
                {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_USER, rowUserId);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case FEEDS:
                long rowFeedId = mDb.insert(Constants.FEED_TABLE_NAME, "", values);
                if (rowFeedId > 0)
                {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_FEED, rowFeedId);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case PROFILES:
                long rowProfileId = mDb.insert(Constants.PROFILE_TABLE_NAME, "", values);
                if (rowProfileId > 0)
                {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_PROFILE, rowProfileId);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default:
                throw new SQLException("Failed to add a record into " + uri);
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int count = 0;

        switch (sUriMatcher.match(uri))
        {
            case USERS:
                count = mDb.delete(Constants.USER_TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:
                String userId = uri.getPathSegments().get(1);
                count = mDb.delete(Constants.USER_TABLE_NAME, Constants.USER_ID + " = " + userId +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case FEEDS:
                count = mDb.delete(Constants.FEED_TABLE_NAME, selection, selectionArgs);
                break;
            case FEED_ID:
                String feedId = uri.getPathSegments().get(1);
                count = mDb.delete(Constants.FEED_TABLE_NAME, Constants.FEED_ID + " = " + feedId +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case PROFILES:
                count = mDb.delete(Constants.PROFILE_TABLE_NAME, selection, selectionArgs);
                break;
            case PROFILE_ID:
                String profileId = uri.getPathSegments().get(1);
                count = mDb.delete(Constants.PROFILE_TABLE_NAME, Constants.PROFILE_ID + " = " + profileId +
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
            case USERS:
                count = mDb.update(Constants.USER_TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_ID:
                count = mDb.update(Constants.USER_TABLE_NAME, values, Constants.USER_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case FEEDS:
                count = mDb.update(Constants.FEED_TABLE_NAME, values, selection, selectionArgs);
                break;
            case FEED_ID:
                count = mDb.update(Constants.FEED_TABLE_NAME, values, Constants.FEED_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case PROFILES:
                count = mDb.update(Constants.PROFILE_TABLE_NAME, values, selection, selectionArgs);
                break;
            case PROFILE_ID:
                count = mDb.update(Constants.PROFILE_TABLE_NAME, values, Constants.PROFILE_ID +
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
