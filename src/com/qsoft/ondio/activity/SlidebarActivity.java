package com.qsoft.ondio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;

/**
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 */

public class SlidebarActivity extends FragmentActivity
{
    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] item = {"com.qsoft.ondio.activity.HomeFragment"};
    private static final int HOME = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvOption;
    private ImageView ivProfile;
    private RelativeLayout rlLeftDrawer;
    FragmentTransaction fragmentTransaction;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        setUpUI();
        setUpDataListOption(this);
        setUpListenerController();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"));
        fragmentTransaction.commit();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpUI()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        ivProfile = (ImageView) findViewById(R.id.slide_ivEditProfile);
        rlLeftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
    }

    private void setUpListenerController()
    {
        ivProfile.setOnClickListener(onClickListener);
        lvOption.setOnItemClickListener(onItemClickListener);
    }

    private final ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l)
        {

            switch (index)
            {
                case HOME:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"));
                    fragmentTransaction.commit();
                    setCloseListOption();
            }
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.slide_ivEditProfile:
                    doEditProfile();
                    break;
            }
        }
    };

    private void doEditProfile()
    {
        fragmentTransaction.replace(R.id.content_frame, new ProfileFragment(), "ProfileFragment");
        fragmentTransaction.addToBackStack("ProfileFragment");
        fragmentTransaction.commit();
        setCloseListOption();
    }

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOptionName));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setOpenListOption()
    {
        mDrawerLayout.openDrawer(rlLeftDrawer);
    }

    public void setCloseListOption()
    {
        mDrawerLayout.closeDrawer(rlLeftDrawer);
    }

    public void doBackToPrevious()
    {
        getSupportFragmentManager().popBackStack();
    }
}
