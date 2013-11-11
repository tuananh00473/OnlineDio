package com.qsoft.ondio.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.googlecode.androidannotations.annotations.EBean;

/**
 * User: AnhNT
 * Date: 10/30/13
 * Time: 8:53 AM
 */

@EBean
public class NetworkAvailable
{
    /**
     * @param context context
     */
    public boolean checkNetwork(Context context)
    {
        try
        {
            ConnectivityManager connectivityMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityMng.getActiveNetworkInfo();
            return null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
