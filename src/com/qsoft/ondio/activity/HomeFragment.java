package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.FeedArrayAdapter;
import com.qsoft.ondio.restservice.AccountShared;
import com.qsoft.ondio.util.Constants;

@EFragment(R.layout.home)
public class HomeFragment extends Fragment
{
    private static final String TAG = "HomeFragment";

    @SystemService
    AccountManager accountManager;

    @ViewById(R.id.home_btMenu)
    Button btMenu;

    @ViewById(R.id.home_lvFeeds)
    ListView home_lvFeeds;

    @Bean
    AccountShared accountShared;

    @AfterViews
    void afterViews()
    {
        String userId = accountManager.getUserData(accountShared.getAccount(), Constants.USERDATA_USER_OBJ_ID);

        Uri uri = ContentUris.withAppendedId(Constants.CONTENT_URI_FEED, Integer.parseInt(userId));
        Cursor homeCursor = getActivity().managedQuery(uri, null, null, null, "_id");
        SimpleCursorAdapter mAdapter = FeedArrayAdapter.getSimpleCursorAdapter(getActivity(), homeCursor);
        home_lvFeeds.setAdapter(mAdapter);

        syncNow(accountShared.getAccount());
    }

    public void syncNow(Account account)
    {
        Bundle bundle = new Bundle();
        ContentResolver.setIsSyncable(account, Constants.PROVIDER_NAME, 1);
        ContentResolver.setSyncAutomatically(account, Constants.PROVIDER_NAME, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        ContentResolver.requestSync(account, Constants.PROVIDER_NAME, bundle);
    }

    @ItemClick(R.id.home_lvFeeds)
    void ClickItem(int index)
    {
        doShowProgram(index);
    }

    private void doShowProgram(int index)
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProgramFragment_(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    @Click(R.id.home_btMenu)
    void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }
}

