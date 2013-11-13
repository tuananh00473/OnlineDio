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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.FeedArrayAdapter;
import com.qsoft.ondio.util.Constants;

public class HomeFragment extends Fragment
{
    private static final String TAG = "HomeFragment";
    private Button btMenu;
    private Button btLoadData;
    private ListView home_lvFeeds;
    private Cursor homeCursor;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Cursor cursor = getActivity().managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_id");
        if (null != cursor && cursor.moveToNext())
        {
            String userId = cursor.getString(cursor.getColumnIndex(Constants.USER_USER_ID));
            Uri uri = ContentUris.withAppendedId(Constants.CONTENT_URI_FEED, Integer.parseInt(userId));
            homeCursor = getActivity().managedQuery(uri, null, null, null, "_id");
            AccountManager accountManager = AccountManager.get(getActivity().getApplicationContext());
            Account account = accountManager.getAccountsByType(Constants.ARG_ACCOUNT_TYPE)[0];
            syncNow(account);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        setUpUI(view);
        SimpleCursorAdapter mAdapter = FeedArrayAdapter.getSimpleCursorAdapter(getActivity(), homeCursor);
        home_lvFeeds.setAdapter(mAdapter);

        setUpListenerController();
        return view;
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

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        btLoadData.setOnClickListener(onClickListener);
        home_lvFeeds.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            doShowProgram();
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.home_btMenu:
                    showMenu();
                    break;
                case R.id.home_btNotifications:
                    break;
            }
        }
    };


    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doShowProgram()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new ProgramFragment_(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view)
    {
        btMenu = (Button) view.findViewById(R.id.home_btMenu);
        btLoadData = (Button) view.findViewById(R.id.home_btNotifications);
        home_lvFeeds = (ListView) view.findViewById(R.id.home_lvFeeds);
    }
}

