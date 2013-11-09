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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.util.Constants;

/**
 * User: AnhNT
 * Date: 10/16/13
 * Time: 8:56 AM
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private Button btLogin;
    private Button btLoginFb;
    private AccountManager mAccountManager;
    private String authToken;
    private Account mConnectedAccount;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAccountManager = AccountManager.get(this);
        setupUI();
        setUpListenerController();
    }

    private void setupUI()
    {
        btLogin = (Button) findViewById(R.id.btLogin);
        btLoginFb = (Button) findViewById(R.id.btLoginFb);
    }


    private void setUpListenerController()
    {
        btLogin.setOnClickListener(onClickListener);
        btLoginFb.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.btLogin:
                    doLogin();
                    break;
                case R.id.btLoginFb:

                    break;
            }
        }
    };

    private void doLogin()
    {
        getTokenForAccountCreateIfNeeded(Constants.ARG_ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        try
                        {
                            Bundle bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            Log.d(TAG, "authen = " + authToken);
                            if (null != authToken)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, Constants.ARG_ACCOUNT_TYPE);

                                String userId = mAccountManager.getUserData(mConnectedAccount, Constants.USERDATA_USER_OBJ_ID);
                                ContentValues values = new ContentValues();
                                values.put(Constants.USER_ACCESS_TOKEN, authToken);
                                values.put(Constants.USER_USER_ID, userId);
                                getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);

                                startActivity(new Intent(getBaseContext(), SlidebarActivity.class));
                            }
                            else
                            {
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
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
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
