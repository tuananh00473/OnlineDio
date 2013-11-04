/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.qsoft.ondio.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.util.Constants;

import java.io.IOException;
import java.util.ArrayList;

/**
 * OnlineSyncAdapter implementation for syncing sample OnlineSyncAdapter contacts to the
 * platform ContactOperations provider.  This sample shows a basic 2-way
 * sync between the client and a sample server.  It also contains an
 * example of how to update the contacts' status messages, which
 * would be useful for a messaging or social networking client.
 */
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
        // Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null)
        {
            for (String key : extras.keySet())
            {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }

        Log.d("udinic", TAG + "> onPerformSync for account[" + account.name + "]. Extras: " + sb.toString());

        try
        {
            // Get the auth token for the current account and
            // the userObjectId, needed for creating items on Parse.com account
            String authToken = mAccountManager.blockingGetAuthToken(account,
                    Constants.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            String userObjectId = mAccountManager.getUserData(account,
                    Constants.USERDATA_USER_OBJ_ID);

            Log.d("udinic", TAG + "> Get remote TV Shows");
            // Get shows from remote

//            JsonResult result = Constants.sServerAuthenticate.getHomeFeed(authToken);
//            List<Feed> remoteFeeds = (List<Feed>) result.getData();
            ArrayList<Feed> remoteFeeds = Constants.sServerAuthenticate.getHomeFeed(authToken);

            Log.d("udinic", TAG + "> Get local TV Shows");
            // Get shows from local
            ArrayList<Feed> localFeeds = new ArrayList<Feed>();
            Cursor c = provider.query(Constants.CONTENT_URI_FEED, null, null, null, null);
            if (c != null)
            {
                while (c.moveToNext())
                {

                    localFeeds.add(Feed.fromCursor(c));
                }
                c.close();
            }

            // See what Local shows are missing on Remote
            ArrayList<Feed> showsToRemote = new ArrayList<Feed>();
            for (Feed localFeed : localFeeds)
            {
                if (!remoteFeeds.contains(localFeed))
                {
                    showsToRemote.add(localFeed);
                }
            }

            // See what Remote shows are missing on Local
            ArrayList<Feed> showsToLocal = new ArrayList<Feed>();
            for (Feed remoteFeed : remoteFeeds)
            {
                if (!localFeeds.contains(remoteFeed)) // TODO REMOVE THIS
                {
                    showsToLocal.add(remoteFeed);
                }
            }

            if (showsToRemote.size() == 0)
            {
                Log.d("udinic", TAG + "> No local changes to update server");
            }
            else
            {
                Log.d("udinic", TAG + "> Updating remote server with local changes");

                // Updating remote tv shows
                for (Feed remoteFeed : showsToRemote)
                {
                    Log.d("udinic", TAG + "> Local -> Remote [" + remoteFeed.display_name + "]");
                    Constants.sServerAuthenticate.putShow(authToken, userObjectId, remoteFeed);
                }
            }

            if (showsToLocal.size() == 0)
            {
                Log.d("udinic", TAG + "> No server changes to update local database");
            }
            else
            {
                Log.d("udinic", TAG + "> Updating local database with remote changes");

                // Updating local tv shows
                int i = 0;
                ContentValues showsToLocalValues[] = new ContentValues[showsToLocal.size()];
                for (Feed localFeed : showsToLocal)
                {
                    Log.d("udinic", TAG + "> Remote -> Local [" + localFeed.display_name + "]");
                    showsToLocalValues[i++] = localFeed.getContentValues();
                }
                provider.bulkInsert(Constants.CONTENT_URI_FEED, showsToLocalValues);
            }

            Log.d("udinic", TAG + "> Finished.");

        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        }
        catch (AuthenticatorException e)
        {
            syncResult.stats.numAuthExceptions++;
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

