package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.util.Common;

public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private Button btLogin;
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
    }


    private void setUpListenerController()
    {
        btLogin.setOnClickListener(onClickListener);
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
            }
        }
    };

    private void doLogin()
    {
        getTokenForAccountCreateIfNeeded(Common.ARG_ACCOUNT_TYPE, Common.AUTHTOKEN_TYPE_FULL_ACCESS);
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
                                mConnectedAccount = new Account(accountName, Common.ARG_ACCOUNT_TYPE);
                                Log.d(TAG, "1");
                                syncNow();
                                Log.d(TAG, "2");
                                Intent intent = new Intent(getBaseContext(), SlidebarActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                intent.putExtra("IS_ADDING_ACCOUNT", true);
                                startActivity(intent);
                            }
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

    private void syncNow()
    {
        Log.d(TAG, mConnectedAccount.name);
        if (mConnectedAccount == null)
        {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        Log.d(TAG, "3");
        ContentResolver.requestSync(mConnectedAccount, Common.PROVIDER_NAME, bundle);
    }
}
