package com.qsoft.ondio.activity;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;
import java.util.ArrayList;

public class SlidebarActivity extends FragmentActivity
{
    String[] listOption = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvOption;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar);

        setUpFindViewById();
        setUpDataListOption(this);

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

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOption));
    }

    private void setUpFindViewById()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
