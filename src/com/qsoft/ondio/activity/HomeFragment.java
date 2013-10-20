package com.qsoft.ondio.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterCustom;
import com.qsoft.ondio.model.Feed;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private Button btMenu;
    private ListView home_lvFeeds;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, null);
        setUpUI(view);
        ArrayList<Feed> feedList = new ArrayList<Feed>();
        setUpDataToHomeListView(getActivity(), feedList);
        setUpListenerController();
        return view;
    }

    private void setUpListenerController()
    {
        btMenu.setOnClickListener(onClickListener);
        home_lvFeeds.setOnItemClickListener(onItemClickListener);
    }

    private void setUpDataToHomeListView(Context context, ArrayList<Feed> feedList)
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
        home_lvFeeds.setAdapter(new ArrayAdapterCustom(context, R.layout.home_listfeeds, feedList));
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
        // do show menu
    }

    private void doShowProgram()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.slidebar_flHomeFragment, new ProgramFragment(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view)
    {
        btMenu = (Button) view.findViewById(R.id.home_btMenu);
        home_lvFeeds = (ListView) view.findViewById(R.id.home_lvFeeds);
    }
}

