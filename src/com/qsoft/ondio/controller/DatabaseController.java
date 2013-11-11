package com.qsoft.ondio.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/11/13
 * Time: 3:54 PM
 */

@EBean
public class DatabaseController
{
    private static final String TAG = "DatabaseController";

    @RootContext
    Activity activity;

    public void saveUserToDB(ContentValues values)
    {
        Cursor c = activity.managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
        if (c.moveToFirst())
        {
            int k = activity.getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);
            if (0 != k)
            {
                Log.d(TAG, "updated user.");
            }
        }
        else
        {
            Uri uri = activity.getContentResolver().insert(Constants.CONTENT_URI_USER, values);
            if (null != uri)
            {
                Log.d(TAG, "inserted user.");
            }
        }
    }

}
