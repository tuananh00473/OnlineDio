package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.controller.DatabaseController;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Constants;
import com.qsoft.ondio.util.NetworkAvailable;

/**
 * User: anhnt
 * Date: 10/12/13
 * Time: 11:21 AM
 */

@EActivity(R.layout.login)
public class LoginActivity extends AccountAuthenticatorActivity
{
    private static final String TAG = "LoginActivity";

    @ViewById(R.id.login_btNext)
    Button btLogin;

    @ViewById(R.id.login_etEmail)
    EditText etEmail;

    @ViewById(R.id.login_etPassword)
    EditText etPassword;

    @SystemService
    AccountManager mAccountManager;

    @Bean
    DatabaseController databaseController;

    @Bean
    NetworkAvailable network;

    @AfterTextChange({R.id.login_etEmail, R.id.login_etPassword})
    void afterTextChanged()
    {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
        {
            btLogin.setBackgroundResource(R.drawable.login_login_visible);
            btLogin.setEnabled(false);
        }
        else
        {
            btLogin.setBackgroundResource(R.drawable.login_login);
            btLogin.setEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Click(R.id.login_btNext)
    void doLogin()
    {
        if (!checkNetwork())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.error_connect_network));
        }
        else if (!checkTimeout())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.connection_timeout));
        }
        else if (!checkUnrecognized())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.service_unrecognized));
        }
        else
        {
            checkLogin();
        }
    }

    private boolean checkUnrecognized()
    {
        return true;
    }

    private boolean checkTimeout()
    {
        return true;
    }

    private boolean checkNetwork()
    {
        return network.checkNetwork(this);
    }

    public void checkLogin()
    {
        Log.d(TAG, "> Submit");
        final String userName = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        new AsyncTask<String, Void, Intent>()
        {
            @Override
            protected Intent doInBackground(String... params)
            {
                Log.d(TAG, "> Started authenticating");
                Bundle data = new Bundle();
                try
                {
                    Log.d(TAG, "before getUser");
                    User user = Constants.sServerAuthenticate.login(userName, password, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
                    Log.d(TAG, "getUser " + user.getUser_id());
                    if (null != user.getAccess_token())
                    {
                        data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, Constants.ARG_ACCOUNT_TYPE);
                        data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());
                        data.putBundle(AccountManager.KEY_USERDATA, getUserBundle(user));
                        data.putString(Constants.PARAM_USER_PASS, password);

                        ContentValues values = user.getContentValues();
                        Log.d(TAG, "Values to String : " + values.toString());
                        databaseController.saveUserToDB(values);
                    }
                    else
                    {
                        data.putString(Constants.KEY_ERROR_MESSAGE, "Account not exist!");
                    }
                }
                catch (Exception e)
                {
                    data.putString(Constants.KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent)
            {
                if (intent.hasExtra(Constants.KEY_ERROR_MESSAGE))
                {
                    Toast.makeText(LoginActivity.this, intent.getStringExtra(Constants.KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    finishLogin(intent);
                }
            }
        }.execute();
    }

    private Bundle getUserBundle(User user)
    {
        Bundle userData = new Bundle();
        userData.putString(Constants.USERDATA_USER_OBJ_ID, user.getUser_id());
        return userData;
    }

    private void finishLogin(Intent intent)
    {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(Constants.PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(Constants.ARG_IS_ADDING_NEW_ACCOUNT, false))
        {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            Intent intentSlidebar = new Intent(this, SlidebarActivity_.class);
            intentSlidebar.putExtra("token", authtoken);
            startActivity(intentSlidebar);

            Toast.makeText(this, authtoken, Toast.LENGTH_SHORT).show();

            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, Constants.AUTHTOKEN_TYPE_FULL_ACCESS, authtoken);
        }
        else
        {
            Log.d(TAG, "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(Activity.RESULT_OK, intent);
    }

    @Click(R.id.login_btBack)
    void doBack()
    {
        startActivity(new Intent(this, MainActivity_.class));
        LoginActivity.this.finish();
    }
}