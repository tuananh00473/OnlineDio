package com.qsoft.ondio.syncdata;

import android.accounts.Account;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineSyncAdapter extends AbstractThreadedSyncAdapter
{

    private static final String TAG = "OnlineSyncAdapter";

    private final ContentResolver mContentResolver;

    public OnlineSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(TAG, "start sync");
        try
        {
            String authToken = "";
            String userId = "";
            Cursor cursor = provider.query(Constants.CONTENT_URI_USER, null, null, null, "_id");
            if (null != cursor && cursor.moveToNext())
            {
                authToken = cursor.getString(cursor.getColumnIndex(Constants.USER_ACCESS_TOKEN));
                userId = cursor.getString(cursor.getColumnIndex(Constants.USER_USER_ID));
            }

            Log.d(TAG, " ==> Get data from service.");
            ArrayList<Feed> remoteFeeds = Constants.sServerAuthenticate.getHomeFeed(authToken);
            Log.d(TAG, "data : " + remoteFeeds);

            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            HashMap<Long, Feed> map = new HashMap<Long, Feed>();
            for (Feed feed : remoteFeeds)
            {
                feed.setAccount_id(Integer.parseInt(userId));
                map.put(new Long(feed.id), feed);
            }

            Log.d(TAG, " ==> Get data from local.");
            Uri uri = ContentUris.withAppendedId(Constants.CONTENT_URI_FEED, Integer.parseInt(userId));
            Cursor c = provider.query(uri, null, null, null, "_id");
            if (null != c)
            {
                c.moveToFirst();
                while (c.moveToNext())
                {
                    Feed feed = Feed.fromCursor(c);
                    Log.d(TAG, " ==> Feed : " + feed.user_id);
                    Feed checkUpdate = map.get(feed.id);

                    if (null != checkUpdate)
                    {
                        map.remove(feed.id);
                        Uri existedUri = Constants.CONTENT_URI_FEED.buildUpon().appendPath(Long.toString(feed.id)).build();
                        if ((checkUpdate.updated_at != null) &&
                                checkUpdate.updated_at.equals(feed.updated_at)
                                || checkUpdate.likes != feed.likes
                                || checkUpdate.comments != feed.comments)
                        {
                            Log.i(TAG, "Data start update");
                            ContentValues values = feed.getContentValues();
                            values.put(Constants.FEED_ACCOUNT_ID, userId);
                            batch.add(ContentProviderOperation.newUpdate(existedUri)
                                    .withValues(values)
                                    .build());

                            syncResult.stats.numUpdates++;
                        }
                    }
                    else
                    {
                        Uri deleteUri = Constants.CONTENT_URI_FEED.buildUpon().appendPath(Long.toString(feed.id)).build();
                        Log.i(TAG, "Start delete" + deleteUri);
                        batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                        syncResult.stats.numDeletes++;
                    }
                }
            }
            c.close();

            for (Feed feed : map.values())
            {
                Log.i(TAG, "Start insert: record_id = " + feed.id);
                ContentValues values = feed.getContentValues();
                values.put(Constants.FEED_ACCOUNT_ID, userId);
                batch.add(ContentProviderOperation
                        .newInsert(Constants.CONTENT_URI_FEED)
                        .withValues(values)
                        .build());
                syncResult.stats.numInserts++;
            }
            mContentResolver.applyBatch(Constants.PROVIDER_NAME, batch);
            mContentResolver.notifyChange(Constants.CONTENT_URI_FEED, null, false);

            Log.d(TAG, " ==> Finished.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

