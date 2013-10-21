package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.qsoft.ondio.R;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.util.NetworkAvailable;

/**
 * User: anhnt
 * Date: 10/12/13
 * Time: 11:21 AM
 */
public class LoginActivity extends Activity
{
    private static final String TAG = "LoginActivity";

    private Button btLogin;
    private Button btBack;
    private TextView tvForgotPassword;
    private EditText etEmail;
    private EditText etPassword;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        btLogin.setEnabled(false);
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
//        if (!checkNetwork())
//        {
//            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.error_connect_network));
//        }
//        else
        if (!checkTimeout())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.connection_timeout));
        }
        else if (!checkUnrecognized())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.service_unrecognized));
        }
        else if (!checkLogin())
        {
            MyDialog.showMessageDialog(this, getString(R.string.tittle_login_error), getString(R.string.incorrect_email_or_password));
        }
        else
        {
            startActivity(new Intent(this, SlidebarActivity.class));
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

    private boolean checkLogin()
    {
        if ((etEmail.getText().toString().trim().equals("sa")))
        {
            if ((etPassword.getText().toString().trim().equals("sa")))
            {
                return true;
            }
        }
        return false;
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
}