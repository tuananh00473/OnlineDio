package com.qsoft.ondio.activity;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;
import com.qsoft.ondio.util.Constants;

/**
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 */

public class SlidebarActivity extends FragmentActivity
{
    private static final String TAG = "SlidebarActivity";

    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int REQUEST_CODE_RETURN_COMMENT = 777;

    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] item = {"com.qsoft.ondio.activity.HomeFragment"};
    private static final int HOME = 0;
    private static final int SIGN_OUT = 7;

    private DrawerLayout mDrawerLayout;
    private ListView lvOption;
    private RelativeLayout rlLeftDrawer;
    private RelativeLayout slidebar_rlProfile;
    FragmentTransaction fragmentTransaction;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        setUpUI();
        setUpDataListOption(this);
        setUpListenerController();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"));
        fragmentTransaction.commit();

    }

    private void setUpUI()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        rlLeftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        slidebar_rlProfile = (RelativeLayout) findViewById(R.id.slidebar_rlProfile);
    }

    private void setUpListenerController()
    {
        slidebar_rlProfile.setOnClickListener(onClickListener);
        lvOption.setOnItemClickListener(onItemClickListener);
    }

    private final ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l)
        {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (index)
            {
                case HOME:
                    ft.replace(R.id.content_frame, new HomeFragment(), "HomeFragment");
                    ft.addToBackStack("HomeFragment");
                    ft.commit();
                    break;
                case SIGN_OUT:
                    AccountManager mAccountManager = AccountManager.get(SlidebarActivity.this);
                    Cursor cursor = managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
                    if (null != cursor && cursor.moveToNext())
                    {
                        String authenToken = cursor.getString(cursor.getColumnIndex(Constants.USER_ACCESS_TOKEN));
                        mAccountManager.invalidateAuthToken(Constants.ARG_ACCOUNT_TYPE, authenToken);
                    }

                    getContentResolver().delete(Constants.CONTENT_URI_USER, null, null);
                    getContentResolver().delete(Constants.CONTENT_URI_FEED, null, null);
                    getContentResolver().delete(Constants.CONTENT_URI_PROFILE, null, null);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("IS_ADDING_ACCOUNT", true);
                    startActivity(intent);
                    break;
            }
            setCloseListOption();
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.slidebar_rlProfile:
                    doEditProfile();
                    break;
            }
        }
    };

    private void doEditProfile()
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProfileFragment(), "ProfileFragment");
        ft.addToBackStack("ProfileFragment");
        ft.commit();
        setCloseListOption();
    }

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new ArrayAdapterListOption(context, R.layout.slidebar_listoptions, listOptionName));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        Fragment fragment = null;
        switch (requestCode)
        {
            case REQUEST_CODE_CAMERA_TAKE_PICTURE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case REQUEST_CODE_RESULT_LOAD_IMAGE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case REQUEST_CODE_RETURN_COMMENT:
                fragment = getSupportFragmentManager().findFragmentById(R.id.program_flInformation);
                break;
        }
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
}
