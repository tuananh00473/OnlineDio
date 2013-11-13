package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;

/**
 * User: AnhNT
 * Date: 10/16/13
 * Time: 8:56 AM
 */

@EActivity(R.layout.input_comment)
public class InputCommentActivity extends Activity
{
    @ViewById(R.id.inputcommnet_btDone)
    Button btDone;

    @ViewById(R.id.inputcomment_etInputComment)
    EditText etContent;

    @AfterViews
    void afterViews()
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Click(R.id.inputcommnet_btDone)
    void doDone()
    {
        Intent intent = getIntent();
        intent.putExtra("data", etContent.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click(R.id.inputcommnet_btBack)
    void doBack()
    {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @AfterTextChange(R.id.inputcomment_etInputComment)
    void afterTextChanged()
    {
        if (etContent.getText().toString().isEmpty())
        {
            btDone.setBackgroundResource(R.drawable.done_visible);
            btDone.setEnabled(false);
        }
        else
        {
            btDone.setBackgroundResource(R.drawable.done);
            btDone.setEnabled(true);
        }
    }
}
