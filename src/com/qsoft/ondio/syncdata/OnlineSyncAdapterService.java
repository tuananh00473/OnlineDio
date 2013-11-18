package com.qsoft.ondio.syncdata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * User: anhnt
 * Date: 11/5/13
 * Time: 9:28 AM
 */
public class OnlineSyncAdapterService extends Service
{
    private static final Object sSyncAdapterLock = new Object();

    private static OnlineSyncAdapter sSyncAdapter;

    @Override
    public void onCreate()
    {
        synchronized (sSyncAdapterLock)
        {
            if (sSyncAdapter == null)
            {
                sSyncAdapter = OnlineSyncAdapter_.getInstance_(getApplicationContext());
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
