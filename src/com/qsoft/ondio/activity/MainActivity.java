package com.qsoft.ondio.activity;

import android.app.Activity;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.ondio.R;
import com.qsoft.ondio.controller.LoginController;

/**
 * User: AnhNT
 * Date: 10/16/13
 * Time: 8:56 AM
 */

@EActivity(R.layout.main)
public class MainActivity extends Activity
{
    @ViewById(R.id.btLogin)
    Button btLogin;

    @Bean
    LoginController loginController;
}
