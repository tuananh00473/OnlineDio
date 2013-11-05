package com.qsoft.ondio.syncdata;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/5/13
 * Time: 10:59 AM
 */
public class SyncData
{
    public static void syncNow(Account account)
    {
        Bundle bundle = new Bundle();
        ContentResolver.setIsSyncable(account, Constants.PROVIDER_NAME, 1);
        ContentResolver.setSyncAutomatically(account, Constants.PROVIDER_NAME, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        ContentResolver.requestSync(account, Constants.PROVIDER_NAME, bundle);
    }
}
