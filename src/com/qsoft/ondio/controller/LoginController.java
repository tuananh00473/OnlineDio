package com.qsoft.ondio.controller;

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
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.LoginActivity;
import com.qsoft.ondio.activity.SlidebarActivity;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/11/13
 * Time: 11:59 AM
 */
@EBean
public class LoginController
{
    private static final String TAG = "LoginController";
    @SystemService
    AccountManager mAccountManager;

    @RootContext
    Activity activity;

    @Click(R.id.btLogin)
    void doLogin()
    {
        getTokenForAccountCreateIfNeeded(Constants.ARG_ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, activity, null, null,
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
                                activity.getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);

                                activity.startActivity(new Intent(activity, SlidebarActivity.class));
                            }
                            else
                            {
                                Intent intent = new Intent(activity, LoginActivity.class);
                                intent.putExtra("IS_ADDING_ACCOUNT", true);
                                activity.startActivity(intent);
                            }
                            activity.finish();
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

        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
