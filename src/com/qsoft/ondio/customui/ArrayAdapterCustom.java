package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.model.Feed;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/12/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayAdapterCustom extends ArrayAdapter<Feed>
{
    private TextView home_tvFeed;
    private TextView home_tvUserName;
    private TextView home_tvLike;
    private TextView home_tvComment;
    private TextView home_tvDays;
    private final ArrayList<Feed> feeds;
    private final Context context;

    public ArrayAdapterCustom(Context context, int textViewResourceId, ArrayList<Feed> feeds)
    {
        super(context, textViewResourceId, feeds);
        this.feeds = feeds;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.home_listfeeds, null);
        }
        Feed feed = feeds.get(position);
        setUpViewFindByID(v);
        if (feed != null)
        {
            home_tvFeed.setText(feed.getFeedTitle());
            home_tvUserName.setText(feed.getFeedUserName());
            home_tvLike.setText(feed.getFeedLike());
            home_tvComment.setText(feed.getFeedComment());
            home_tvDays.setText(feed.getFeedDays());
        }
        return v;
    }

    private void setUpViewFindByID(View v)
    {
        home_tvFeed = (TextView) v.findViewById(R.id.home_tvFeed);
        home_tvUserName = (TextView) v.findViewById(R.id.home_tvUserName);
        home_tvLike = (TextView) v.findViewById(R.id.home_tvLike);
        home_tvComment = (TextView) v.findViewById(R.id.home_tvNumberComment);
        home_tvDays = (TextView) v.findViewById(R.id.home_tvDays);
    }
}