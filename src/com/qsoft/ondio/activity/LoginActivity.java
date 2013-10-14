package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Button btLogin;
    private Button btBack;
    private TextView tvForgotPassword;
    private EditText etEmail;
    private EditText etPassword;
    private NetworkAvailable network;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etEmail = (EditText) findViewById(R.id.login_etEmail);
        etPassword = (EditText) findViewById(R.id.login_etPassword);

        btLogin = (Button) findViewById(R.id.login_button_next);
        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doLogin();
            }
        });
        btBack = (Button) findViewById(R.id.login_button_back);
        btBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doBack();
            }
        });

        tvForgotPassword = (TextView) findViewById(R.id.login_tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doForgotPassword();
            }
        });
    }

    private void doLogin()
    {
        if (!checkNetwork())
        {
            MyDialog.showMessageDialog(LoginActivity.this, getString(R.string.tittle_login_error), getString(R.string.error_connect_network));
        }
        else if (!checkTimeout())
        {
            MyDialog.showMessageDialog(LoginActivity.this, getString(R.string.tittle_login_error), getString(R.string.connection_timeout));
        }
        else if (!checkUnrecognized())
        {
            MyDialog.showMessageDialog(LoginActivity.this, getString(R.string.tittle_login_error), getString(R.string.service_unrecognized));
        }
        else if (!checkLogin())
        {
            MyDialog.showMessageDialog(LoginActivity.this, getString(R.string.tittle_login_error), getString(R.string.incorrect_email_or_password));
        }
        else
        {
            // doc them ve fragment
            // http://developer.android.com/reference/android/support/v4/app/Fragment.html
            startActivity(new Intent(this, HomeActivity.class));
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
        network = new NetworkAvailable(this);
//        return network.isEnabled();
        return true;
    }

    private boolean checkLogin()
    {
        if (etEmail.getText().toString().trim().equals("sa") && etPassword.getText().toString().trim().equals("sa"))
        {
            return true;
        }
        return false;
    }

    private void doBack()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void doForgotPassword()
    {

    }


}