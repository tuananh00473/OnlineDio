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

public class SlidebarActivity extends FragmentActivity {
    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] item = {"HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment"};
    private static final int HOME = 0;
    private static final int FAVORITE = 1;
    private static final int FOLLOWING = 2;
    private static final int AUDIENCE = 3;
    private static final int GENRES = 4;
    private static final int SETTING = 5;
    private static final int HELP_CENTER = 6;
    private static final int SIGN_OUT = 7;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvOption;
    private ImageView ivProfile;
    private RelativeLayout rlLeftDrawer;
    private HomeFragment homeFragment;

    protected void onCreate(Bundle savedInstanceState) {
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"));
        fragmentTransaction.commit();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        ivProfile = (ImageView) findViewById(R.id.slide_ivEditProfile);
        rlLeftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);

        homeFragment = new HomeFragment();
    }

    private void setUpListenerController() {
        ivProfile.setOnClickListener(onClickListener);
        lvOption.setOnItemClickListener(onItemClickListener);
    }

    private final ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l) {
            mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    switch (index) {
                        case HOME:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case FAVORITE:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case FOLLOWING:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case AUDIENCE:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case GENRES:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case SETTING:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case HELP_CENTER:
                            ft.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, item[HOME]));
                            break;
                        case SIGN_OUT:
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            break;
                    }
                    ft.commit();
                    setCloseListOption();

                }
            });
//            mDrawerLayout.closeDrawer(lvOption);
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.slide_ivEditProfile:
                    doEditProfile();
                    break;
            }
        }
    };

    private void doEditProfile() {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProfileFragment(), "ProfileFragment");
        ft.addToBackStack("ProfileFragment");
        ft.commit();
    }

    private void setUpDataListOption(Context context) {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOptionName));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setOpenListOption() {
        mDrawerLayout.openDrawer(rlLeftDrawer);
    }

    public void setCloseListOption() {
        mDrawerLayout.closeDrawer(rlLeftDrawer);
    }
}
