package com.qsoft.ondio.activity;

import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.util.Constants;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private static final String TAG = "HomeFragment";
    private Button btMenu;
    private ListView home_lvFeeds;
    private ArrayList<Feed> feedList;
    private Account mConnectedAccount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        setUpUI(view);
        setUpDataToHomeListView(getActivity());
        setUpListenerController();
        return view;
    }

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        home_lvFeeds.setOnItemClickListener(onItemClickListener);
    }

    private void setUpDataToHomeListView(Context context)
    {
        try
        {
            Log.d(TAG, "xxx");
            Cursor c = getActivity().managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
            if (c.moveToFirst())
            {
                feedList = Constants.sServerAuthenticate.getHomeFeed(c.getString(c.getColumnIndex(Constants.USER_ACCESS_TOKEN)));
                home_lvFeeds.setAdapter(new ArrayAdapterCustom(context, R.layout.home_listfeeds, feedList));
//                Log.d(TAG, "feedlist : " + feedList.toString());
//                Feed feed = new Feed();
//                feed.setId(Integer.parseInt(c.getString(c.getColumnIndex(Constants.FEED_ID))));
//                feed.setTitle(c.getString(c.getColumnIndex(Constants.FEED_TITLE)));
//                feed.setUsername(c.getString(c.getColumnIndex(Constants.FEED_USERNAME)));
//                feed.setLikes(Integer.parseInt(c.getString(c.getColumnIndex(Constants.FEED_LIKES))));
//                feed.setComments(Integer.parseInt(c.getString(c.getColumnIndex(Constants.FEED_COMMENTS))));
//                feed.setViewed(Integer.parseInt(c.getString(c.getColumnIndex(Constants.FEED_VIEWED))));
//
//                feedList.add(feed);
            }
//            home_lvFeeds.setAdapter(new ArrayAdapterCustom(context, R.layout.home_listfeeds, feedList));
        }
        catch (Exception e)
        {

        }
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
        ft.replace(R.id.content_frame, new ProgramFragment(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view)
    {
        btMenu = (Button) view.findViewById(R.id.home_btMenu);
        home_lvFeeds = (ListView) view.findViewById(R.id.home_lvFeeds);
    }
}

