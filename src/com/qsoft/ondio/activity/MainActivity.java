package com.qsoft.ondio.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.ondio.R;

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

    @AfterViews
    void setUpView()
    {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }
    }

    @Click(R.id.btLogin)
    void doLogin()
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity_.class);
        startActivity(intent);
        finish();
    }
}
