package com.qsoft.ondio.activity;

import android.app.Activity;
import android.os.Bundle;
import com.qsoft.ondio.R;


public class InputCommentActivity extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_comment);
        setViewByFindId();
        setUpListenerController();
    }

    private void setUpListenerController()
    {
    }

    private void setViewByFindId()
    {
    }
}
