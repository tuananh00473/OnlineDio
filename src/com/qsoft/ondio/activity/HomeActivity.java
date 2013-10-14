package com.qsoft.ondio.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
public class HomeActivity extends Activity
{
    ListView home_lvFeeds;
    private SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        setUpViewHome();
        ArrayList<Feed> feedList = new ArrayList<Feed>();
        setUpDataToHomeListView(this, feedList);

    }

    private void setUpDataToHomeListView(HomeActivity homeActivity, ArrayList<Feed> feedList)
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
        home_lvFeeds.setAdapter(new ArrayAdapterCustom(homeActivity, android.R.layout.simple_selectable_list_item, feedList));
    }

    private void setUpViewHome()
    {
        home_lvFeeds = (ListView) findViewById(R.id.home_lvFeeds);
    }
}
