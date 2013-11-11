package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
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
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
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

    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int REQUEST_CODE_RETURN_COMMENT = 777;

    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    //    final String[] listOptionName = getResources().getStringArray(R.array.listOptionName);
    private static final int HOME = 0;
    private static final int SIGN_OUT = 7;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.slidebar_listOption)
    ListView lvOption;

    @ViewById(R.id.left_drawer)
    RelativeLayout rlLeftDrawer;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        setUpUI();
        setUpDataListOption(this);
        setUpListenerController();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, Fragment.instantiate(SlidebarActivity.this, "com.qsoft.ondio.activity.HomeFragment"));
        fragmentTransaction.commit();

    }

    private void setUpUI()
    {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void setUpListenerController()
    {
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
                    break;
            }
            setCloseListOption();
        }
    };

    @Click(R.id.slidebar_rlProfile)
    void doEditProfile()
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
