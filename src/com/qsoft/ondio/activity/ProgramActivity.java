package com.qsoft.ondio.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import com.qsoft.ondio.R;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/17/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProgramActivity extends FragmentActivity
{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        setUpUI();
        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.program_flInformation, Fragment.instantiate(ProgramActivity.this, "com.qsoft.ondio.activity.InformationSoundFragment"))
                .commit();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpUI()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}
