package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.qsoft.ondio.R;

/**
 * User: anhnt
 * Date: 10/15/13
 * Time: 5:35 PM
 */
public class SignUpActivity extends Activity
{
    private Button btBack;
    private Button btNext;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordAgain;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etEmail = (EditText) findViewById(R.id.signup_etEmail);
        etEmail.addTextChangedListener(textChangeListener);
        etPassword = (EditText) findViewById(R.id.signup_etPassword);
        etPasswordAgain = (EditText) findViewById(R.id.signup_etPasswordAgain);

        btBack = (Button) findViewById(R.id.signup_btBack);
        btBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

        btNext = (Button) findViewById(R.id.signup_btNext);
        btNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doSignUp();
            }
        });
    }

    private void doSignUp()
    {
        // do sign up here
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
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
            if (!etEmail.getText().toString().isEmpty())
            {
                btNext.setBackgroundResource(R.drawable.signup_next);
            }
            else
            {
                btNext.setBackgroundResource(R.drawable.signup_next_visible);
            }
        }
    };
}