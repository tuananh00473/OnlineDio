package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.ondio.R;
import com.qsoft.ondio.util.Constants;

/**
 * User: AnhNT
 * Date: 10/16/13
 * Time: 8:56 AM
 */

@EActivity(R.layout.main)
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";

    @SystemService
    AccountManager mAccountManager;

    @Click(R.id.btLogin)
    void doLogin()
    {
        getTokenForAccountCreateIfNeeded(Constants.ARG_ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        try
                        {
                            Bundle bnd = future.getResult();
                            String authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            Log.d(TAG, "authen = " + authToken);
                            if (null != authToken)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                Account account = new Account(accountName, Constants.ARG_ACCOUNT_TYPE);

                                String userId = mAccountManager.getUserData(account, Constants.USERDATA_USER_OBJ_ID);
                                ContentValues values = new ContentValues();
                                values.put(Constants.USER_ACCESS_TOKEN, authToken);
                                values.put(Constants.USER_USER_ID, userId);
                                getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);

                                startActivity(new Intent(MainActivity.this, SlidebarActivity_.class));
                            }
                            else
                            {
                                Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
                                intent.putExtra("IS_ADDING_ACCOUNT", true);
                                startActivity(intent);
                            }
                            MainActivity.this.finish();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    }

    private void showMessage(final String msg)
    {
        if (msg == null || msg.trim().equals(""))
        {
            return;
        }

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
