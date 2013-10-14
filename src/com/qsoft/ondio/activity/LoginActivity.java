package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.qsoft.ondio.R;

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

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btLogin = (Button) findViewById(R.id.login_button_next);
        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
        btBack = (Button) findViewById(R.id.login_button_back);
        btBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        tvForgotPassword = (TextView) findViewById(R.id.login_tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }
}