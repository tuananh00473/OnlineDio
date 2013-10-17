package com.qsoft.ondio.activity;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;

public class SlidebarActivity extends FragmentActivity
{
    String[] listOption = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvOption;
    private ImageView ivProfile;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        setUpFindViewById();
        setUpDataListOption(this);
        setUpListenerController();

        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"))
                .commit();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpListenerController()
    {
        ivProfile.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.slide_ivEditProfile:
                    doProfile();

            }
        }
    };

    private void doProfile()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOption));
    }

    private void setUpFindViewById()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        ivProfile = (ImageView) findViewById(R.id.slide_ivEditProfile);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
