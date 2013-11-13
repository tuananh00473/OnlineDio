package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterListOption;
import com.qsoft.ondio.util.Constants;

/**
 * User: thinhdd
 * Date: 10/16/13
 * Time: 1:55 PM
 */

@EActivity(R.layout.slidebar)
public class SlidebarActivity extends FragmentActivity
{
    private static final String TAG = "SlidebarActivity";

    private static final String HOME_FRAGMENT = "HomeFragment";
    private static final String PROFILE_FRAGMENT = "ProfileFragment";
    public static final String FRAGMENT_FIRST_VIEW = "com.qsoft.ondio.activity.HomeFragment_";

    private static final int HOME = 0;
    private static final int SIGN_OUT = 7;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.slidebar_listOption)
    ListView lvOption;

    @ViewById(R.id.left_drawer)
    RelativeLayout rlLeftDrawer;

    @AfterViews
    void afterView()
    {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        String[] listOptionName = getResources().getStringArray(R.array.listOptionName);
        lvOption.setAdapter(new ArrayAdapterListOption(this, R.layout.slidebar_listoptions, listOptionName));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, FRAGMENT_FIRST_VIEW));
        fragmentTransaction.commit();
    }

    @ItemClick(R.id.slidebar_listOption)
    void onItemClickListener(int index)
    {
        switch (index)
        {
            case HOME:
                showFragment(new HomeFragment_(), HOME_FRAGMENT);
                break;
            case SIGN_OUT:
                doSignOut();
                break;
        }
        setCloseListOption();
    }

    private void doSignOut()
    {
        AccountManager mAccountManager = AccountManager.get(SlidebarActivity.this);
        Account[] accounts = mAccountManager.getAccounts();
        for (Account account : accounts)
        {
            mAccountManager.removeAccount(account, null, null);
        }

        getContentResolver().delete(Constants.CONTENT_URI_USER, null, null);

        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
        intent.putExtra("IS_ADDING_ACCOUNT", true);
        startActivity(intent);
        finish();
    }

    @Click(R.id.slidebar_rlProfile)
    void doEditProfile()
    {
        showFragment(new ProfileFragment_(), PROFILE_FRAGMENT);
        setCloseListOption();
    }

    public void showFragment(Fragment fragment, String tittle)
    {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, tittle);
        fragmentTransaction.addToBackStack(tittle);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        Fragment fragment = null;
        switch (requestCode)
        {
            case Constants.REQUEST_CODE_CAMERA_TAKE_PICTURE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case Constants.REQUEST_CODE_RESULT_LOAD_IMAGE:
                fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                break;
            case Constants.REQUEST_CODE_RETURN_COMMENT:
                fragment = getSupportFragmentManager().findFragmentById(R.id.program_flInformation);
                break;
        }
        assert fragment != null;
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
