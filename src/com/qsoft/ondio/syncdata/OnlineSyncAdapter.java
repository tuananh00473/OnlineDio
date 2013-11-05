package com.qsoft.ondio.syncdata;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.util.Constants;

import java.util.ArrayList;

public class OnlineSyncAdapter extends AbstractThreadedSyncAdapter
{

    private static final String TAG = "OnlineSyncAdapter";

    private final AccountManager mAccountManager;

    public OnlineSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
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
//            String authToken = mAccountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE_FULL_ACCESS, true);
//            String userObjectId = mAccountManager.getUserData(account, Constants.USERDATA_USER_OBJ_ID);

            Log.d(TAG, " ==> Get data from service.");

            // Get shows from remote
            ArrayList<Feed> remoteFeeds = Constants.sServerAuthenticate.getHomeFeed(authToken);
            Log.d(TAG, "data : " + remoteFeeds);
            provider.delete(Constants.CONTENT_URI_FEED, null, null);
            Log.d(TAG, "delete");
            for (Feed localFeed : remoteFeeds)
            {
                provider.insert(Constants.CONTENT_URI_FEED, localFeed.getContentValues());
            }

//            Log.d(TAG, " ==> Get data from local.");
//            // Get shows from local
//            ArrayList<Feed> localFeeds = new ArrayList<Feed>();
//            Cursor c = provider.query(Constants.CONTENT_URI_FEED, null, null, null, "_ID");
//            if (c != null)
//            {
//                while (c.moveToNext())
//                {
//                    localFeeds.add(Feed.fromCursor(c));
//                }
//                c.close();
//            }

//            // See what Local shows are missing on Remote
//            ArrayList<Feed> showsToRemote = new ArrayList<Feed>();
//            for (Feed localFeed : localFeeds)
//            {
//                if (!remoteFeeds.contains(localFeed))
//                {
//                    showsToRemote.add(localFeed);
//                }
//            }

//            // See what Remote shows are missing on Local
//            ArrayList<Feed> showsToLocal = new ArrayList<Feed>();
//            for (Feed remoteFeed : remoteFeeds)
//            {
//                if (!localFeeds.contains(remoteFeed)) // TODO REMOVE THIS
//                {
//                    showsToLocal.add(remoteFeed);
//                }
//            }

//            if (showsToRemote.size() == 0)
//            {
//                Log.d("udinic", TAG + "> No local changes to update server");
//            }
//            else
//            {
//                Log.d("udinic", TAG + "> Updating remote server with local changes");
//
//                // Updating remote tv shows
//                for (Feed remoteFeed : showsToRemote)
//                {
//                    Log.d("udinic", TAG + "> Local -> Remote [" + remoteFeed.display_name + "]");
//                    Constants.sServerAuthenticate.putShow(authToken, userObjectId, remoteFeed);
//                }
//            }

//            if (showsToLocal.size() == 0)
//            {
//                Log.d(TAG, " ==> No server changes to update local database");
//            }
//            else
//            {
//                Log.d(TAG, " ==> Updating local database with remote changes");
//                for (Feed localFeed : showsToLocal)
//                {
//                    provider.insert(Constants.CONTENT_URI_FEED, localFeed.getContentValues());
//                }
//            }

            Log.d(TAG, " ==> Finished.");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

