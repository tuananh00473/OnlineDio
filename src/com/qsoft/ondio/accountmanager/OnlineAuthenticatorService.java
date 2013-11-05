package com.qsoft.ondio.accountmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OnlineAuthenticatorService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        OnlineAuthenticator authenticator = new OnlineAuthenticator(this);
        return authenticator.getIBinder();
    }
}
