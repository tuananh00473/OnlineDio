package com.qsoft.ondio.accountmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OnlineAuthenticatorService extends Service
{
    private static final Object sSyncAdapterLock = new Object();
    private static OnlineSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate()
    {
        synchronized (sSyncAdapterLock)
        {
            if (sSyncAdapter == null)
            {
                sSyncAdapter = new OnlineSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        OnlineAuthenticator authenticator = new OnlineAuthenticator(this);
        return authenticator.getIBinder();
    }
}
