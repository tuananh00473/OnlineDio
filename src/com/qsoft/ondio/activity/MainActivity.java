package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.qsoft.ondio.R;

public class MainActivity extends Activity
{
    private RelativeLayout rlBackground;
    private Button btLogin;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rlBackground = (RelativeLayout) findViewById(R.id.rlBackground);
        rlBackground.setBackgroundResource(R.drawable.background);

        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
