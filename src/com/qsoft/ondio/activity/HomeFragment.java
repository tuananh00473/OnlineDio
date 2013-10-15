package com.qsoft.ondio.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.actionbarsherlock.app.SherlockFragment;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.model.Feed;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/14/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends SherlockFragment
{
    ListView home_lvFeeds;
    private SimpleAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.home, container, false);
        super.onCreate(savedInstanceState);
        setUpViewHome(rootView);
        ArrayList<Feed> feedList = new ArrayList<Feed>();
        setUpDataToHomeListView(this, feedList);
        return rootView;
    }

    private void setUpDataToHomeListView(HomeFragment homeActivity, ArrayList<Feed> feedList)
    {
        Feed feed1 = new Feed("ShowGame", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed2 = new Feed("ShowGame1", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed3 = new Feed("ShowGame2", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed4 = new Feed("ShowGame3", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed5 = new Feed("ShowGame4", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed6 = new Feed("ShowGame5", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed7 = new Feed("ShowGame6", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed8 = new Feed("ShowGame7", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed9 = new Feed("ShowGame8", "RuaTre", "Likes: 3", "Comments: 4", "day 5");
        Feed feed10 = new Feed("ShowGame9", "RuaTre", "Likes: 3", "Comments: 4", "day 5");

        feedList.add(feed1);
        feedList.add(feed2);
        feedList.add(feed3);
        feedList.add(feed4);
        feedList.add(feed5);
        feedList.add(feed6);
        feedList.add(feed7);
        feedList.add(feed8);
        feedList.add(feed9);
        feedList.add(feed10);
        home_lvFeeds.setAdapter(new ArrayAdapterCustom(getActivity(), R.layout.home_listfeeds, feedList));
    }

    private void setUpViewHome(View rootView)
    {
        home_lvFeeds = (ListView) rootView.findViewById(R.id.home_lvFeeds);
    }
}
