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
            Cursor cToken = provider.query(Constants.CONTENT_URI_USER, null, null, null, "_ID");
            if (null != cToken && cToken.moveToNext())
            {
                authToken = cToken.getString(cToken.getColumnIndex(Constants.USER_ACCESS_TOKEN));
            }

            Log.d(TAG, " ==> Get data from service.");
            ArrayList<Feed> remoteFeeds = Constants.sServerAuthenticate.getHomeFeed(authToken);
            Log.d(TAG, "data : " + remoteFeeds);

            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            HashMap<Long, Feed> map = new HashMap<Long, Feed>();
            for (Feed feed : remoteFeeds)
            {
                map.put(new Long(feed.id), feed);
            }

            Log.d(TAG, " ==> Get data from local.");
            Cursor c = provider.query(Constants.CONTENT_URI_FEED, null, null, null, "_ID");
            assert c != null;
            while (c.moveToNext())
            {
                Feed feed = Feed.fromCursor(c);
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
                        batch.add(ContentProviderOperation.newUpdate(existedUri)
                                .withValues(feed.getContentValues())
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
            c.close();

            for (Feed feed : map.values())
            {
                Log.i(TAG, "Start insert: record_id = " + feed.id);
                batch.add(ContentProviderOperation
                        .newInsert(Constants.CONTENT_URI_FEED)
                        .withValues(feed.getContentValues())
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

