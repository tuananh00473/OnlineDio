package com.qsoft.ondio.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.qsoft.ondio.R;

import java.util.Calendar;

/**
 * User: anhnt
 * Date: 10/16/13
 * Time: 8:56 AM
 */
public class ProfileActivity extends Activity
{
    private EditText etProfileName;
    private EditText etFullName;
    private EditText etPhoneNo;
    private EditText etBirthday;
    private Button btMale;
    private Button btFemale;
    private EditText etCountry;
    private EditText etDescription;

    private static final int DATE_DIALOG_ID = 0;
    private static final int MALE = 0;
    private static final int FEMALE = 1;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setUpUI();
        setUpListenerController();
    }

    private void setUpListenerController()
    {
        etBirthday.setOnClickListener(onClickListener);
        etCountry.setOnClickListener(onClickListener);
        btMale.setOnClickListener(onClickListener);
        btFemale.setOnClickListener(onClickListener);
    }

    private void setUpUI()
    {
        etProfileName = (EditText) findViewById(R.id.profile_etProfileName);
        etFullName = (EditText) findViewById(R.id.profile_etFullName);
        etPhoneNo = (EditText) findViewById(R.id.profile_etPhoneNo);
        etBirthday = (EditText) findViewById(R.id.profile_etBirthday);
        btMale = (Button) findViewById(R.id.profile_btMale);
        btFemale = (Button) findViewById(R.id.profile_btFemale);
        etCountry = (EditText) findViewById(R.id.profile_etCountry);
        etDescription = (EditText) findViewById(R.id.profile_etDescription);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.profile_etBirthday:
                    showDialog(DATE_DIALOG_ID);
                    break;
                case R.id.profile_etCountry:
                    setCountry();
                    break;
                case R.id.profile_btMale:
                    setGender(MALE);
                    break;
                case R.id.profile_btFemale:
                    setGender(FEMALE);
                    break;
            }
        }
    };

    private void setGender(int gender)
    {
        switch (gender)
        {
            case MALE:
                btMale.setBackgroundResource(R.drawable.profile_male);
                btFemale.setBackgroundResource(R.drawable.profile_female_visible);
                break;
            case FEMALE:
                btFemale.setBackgroundResource(R.drawable.profile_female);
                btMale.setBackgroundResource(R.drawable.profile_male_visible);
                break;
        }
    }


    private void showDate(int year, int monthOfYear, int dayOfMonth)
    {
        etBirthday.setText(dayOfMonth + "/" + ++monthOfYear + "/" + year);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            showDate(year, monthOfYear, dayOfMonth);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private void setCountry()
    {
        Spinner spAddress = (Spinner) findViewById(R.id.profile_spCountry);
        spAddress.performClick();
        spAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                showAddress(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    private void showAddress(String address)
    {
        etCountry.setText(address);
    }
}