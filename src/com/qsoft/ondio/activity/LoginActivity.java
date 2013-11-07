package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.qsoft.ondio.R;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Constants;
import com.qsoft.ondio.util.NetworkAvailable;

/**
 * User: anhnt
 * Date: 10/12/13
 * Time: 11:21 AM
 */
public class LoginActivity extends AccountAuthenticatorActivity
{
    private static final String TAG = "LoginActivity";
    private AccountManager mAccountManager;
    private String mAuthTokenType;

    private Button btLogin;
    private Button btBack;
    private TextView tvForgotPassword;
    private EditText etEmail;
    private EditText etPassword;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAccountManager = AccountManager.get(getBaseContext());
        mAuthTokenType = getIntent().getStringExtra(Constants.ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
        {
            mAuthTokenType = Constants.AUTHTOKEN_TYPE_FULL_ACCESS;
        }

        setUpUI();
        setUpListenerController();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setUpUI()
    {
        etEmail = (EditText) findViewById(R.id.login_etEmail);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        btLogin = (Button) findViewById(R.id.login_btNext);
        btBack = (Button) findViewById(R.id.login_btBack);
        tvForgotPassword = (TextView) findViewById(R.id.login_tvForgotPassword);
    }

    private void setUpListenerController()
    {
        etEmail.addTextChangedListener(textChangeListener);
        etPassword.addTextChangedListener(textChangeListener);
//        btLogin.setEnabled(false);
        btLogin.setOnClickListener(onClickListener);
        btBack.setOnClickListener(onClickListener);
        tvForgotPassword.setOnClickListener(onClickListener);
        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener()
        {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
                {
                    Log.i(TAG, "Enter pressed");
                    doLogin();
                }
                return false;
            }
        });
    }

    private void doLogin()
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
        NetworkAvailable network = new NetworkAvailable(this);
        return network.isEnabled();
    }

    private void doBack()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void doForgotPassword()
    {
        // do forgot password here
    }

    private final TextWatcher textChangeListener = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
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
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.login_btNext:
                    doLogin();
                    break;
                case R.id.login_btBack:
                    doBack();
                    break;
                case R.id.login_tvForgotPassword:
                    doForgotPassword();
                    break;
            }
        }
    };

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
                    User user = Constants.sServerAuthenticate.login(userName, password, mAuthTokenType);
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
                        saveUserToDB(values);
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
                    Toast.makeText(getBaseContext(), intent.getStringExtra(Constants.KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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

    private void saveUserToDB(ContentValues values)
    {
        Cursor c = managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
        if (c.moveToFirst())
        {
            int k = getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);
            if (0 != k)
            {
                Log.d(TAG, "updated user.");
            }
        }
        else
        {
            Uri uri = getContentResolver().insert(Constants.CONTENT_URI_USER, values);
            if (null != uri)
            {
                Log.d(TAG, "inserted user.");
            }
        }
    }

    private void finishLogin(Intent intent)
    {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(Constants.PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(Constants.ARG_IS_ADDING_NEW_ACCOUNT, false))
        {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;
            Intent intentSlidebar = new Intent(getBaseContext(), SlidebarActivity.class);
            intentSlidebar.putExtra("token", authtoken);
            startActivity(intentSlidebar);

            Toast.makeText(getBaseContext(), authtoken, Toast.LENGTH_SHORT).show();

            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        }
        else
        {
            Log.d(TAG, "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
    }
}