package com.qsoft.ondio.syncdata;

import android.accounts.Account;
import android.accounts.AccountManager;
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

    private final AccountManager mAccountManager;
    private final ContentResolver mContentResolver;

    public OnlineSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(TAG, "ok");
        try
        {
            String authToken = "";
            Cursor cToken = provider.query(Constants.CONTENT_URI_USER, null, null, null, "_ID");
            if (null != cToken && cToken.moveToNext())
            {
                authToken = cToken.getString(cToken.getColumnIndex(Constants.USER_ACCESS_TOKEN));
            }

            Log.d(TAG, " ==> Get data from service.");

            // Get shows from remote
            ArrayList<Feed> remoteFeeds = Constants.sServerAuthenticate.getHomeFeed(authToken);
            Log.d(TAG, "data : " + remoteFeeds);

            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            HashMap<Long, Feed> map = new HashMap<Long, Feed>();
            for (Feed feed : remoteFeeds)
            {
                map.put(new Long(feed.id), feed);
            }

            Log.d(TAG, " ==> Get data from local.");
            // Get shows from local
            ArrayList<Feed> localFeeds = new ArrayList<Feed>();
            Cursor c = provider.query(Constants.CONTENT_URI_FEED, null, null, null, "_ID");
            assert c != null;
            while (c.moveToNext())
            {
                Feed feed = Feed.fromCursor(c);
                Feed checkUpdate = map.get(feed.id);

                if (null != checkUpdate)
                {
                    map.remove(feed.id);
                    Uri existedUri = Constants.CONTENT_URI_FEED.buildUpon()
                            .appendPath(Long.toString(feed.id)).build();
                    if ((checkUpdate.updated_at != null) &&
                            checkUpdate.updated_at.equals(feed.updated_at)
                            || checkUpdate.likes != feed.likes
                            || checkUpdate.comments != feed.comments)
                    {
                        Log.i(TAG, "Data start update");
                        batch.add(ContentProviderOperation.newUpdate(existedUri)
                                .withValue(Constants.FEED_ID, feed.id)
                                .withValue(Constants.FEED_USER_ID, feed.user_id)
                                .withValue(Constants.FEED_TITLE, feed.title)
                                .withValue(Constants.FEED_THUMBNAIL, feed.thumbnail)
                                .withValue(Constants.FEED_DESCRIPTION, feed.description)
                                .withValue(Constants.FEED_SOUND_PATH, feed.sound_path)
                                .withValue(Constants.FEED_DURATION, feed.duration)
                                .withValue(Constants.FEED_PLAYED, feed.played)
                                .withValue(Constants.FEED_CREATED_AT, feed.created_at)
                                .withValue(Constants.FEED_UPDATED_AT, feed.updated_at)
                                .withValue(Constants.FEED_LIKES, feed.likes)
                                .withValue(Constants.FEED_VIEWED, feed.viewed)
                                .withValue(Constants.FEED_COMMENTS, feed.comments)
                                .withValue(Constants.FEED_USERNAME, feed.username)
                                .withValue(Constants.FEED_DISPLAY_NAME, feed.display_name)
                                .withValue(Constants.FEED_AVATAR, feed.avatar).build());

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
                        .withValue(Constants.FEED_ID, feed.id)
                        .withValue(Constants.FEED_USER_ID, feed.user_id)
                        .withValue(Constants.FEED_TITLE, feed.title)
                        .withValue(Constants.FEED_THUMBNAIL, feed.thumbnail)
                        .withValue(Constants.FEED_DESCRIPTION, feed.description)
                        .withValue(Constants.FEED_SOUND_PATH, feed.sound_path)
                        .withValue(Constants.FEED_DURATION, feed.duration)
                        .withValue(Constants.FEED_PLAYED, feed.played)
                        .withValue(Constants.FEED_CREATED_AT, feed.created_at)
                        .withValue(Constants.FEED_UPDATED_AT, feed.updated_at)
                        .withValue(Constants.FEED_LIKES, feed.likes)
                        .withValue(Constants.FEED_VIEWED, feed.viewed)
                        .withValue(Constants.FEED_COMMENTS, feed.comments)
                        .withValue(Constants.FEED_USERNAME, feed.username)
                        .withValue(Constants.FEED_DISPLAY_NAME, feed.display_name)
                        .withValue(Constants.FEED_AVATAR, feed.avatar).build());
                syncResult.stats.numInserts++;
            }
            mContentResolver.applyBatch(Constants.PROVIDER_NAME, batch);
            mContentResolver.notifyChange(Constants.CONTENT_URI_FEED, // URI
                    null, // No local observer
                    false); // IMPORTANT: Do not sync to network

            Log.d(TAG, " ==> Finished.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

