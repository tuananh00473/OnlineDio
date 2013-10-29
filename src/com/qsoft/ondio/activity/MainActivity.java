package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.qsoft.ondio.R;

public class MainActivity extends Activity
{
    private Button btLogin;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        Boolean addNewAccount = doCheckTokenCurrent();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("IS_ADDING_ACCOUNT", true);
        startActivity(intent);
    }

    private Boolean doCheckTokenCurrent()
    {
        return true;
    }
}
