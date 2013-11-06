package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.lazzyload.ImageLoader;
import com.qsoft.ondio.model.Feed;

import java.util.ArrayList;

/**
 * User: AnhNT
 * Date: 10/12/13
 * Time: 11:08 AM
 */
public class ArrayAdapterCustom extends ArrayAdapter<Feed>
{
    private TextView home_tvFeed;
    private TextView home_tvUserName;
    private TextView home_tvLike;
    private TextView home_tvComment;
    private TextView home_tvDays;
    private ImageView home_ivAvatar;

    private final ArrayList<Feed> feeds;
    private final Context context;
    public ImageLoader imageLoader;

    public ArrayAdapterCustom(Context context, int textViewResourceId, ArrayList<Feed> feeds)
    {
        super(context, textViewResourceId, feeds);
        this.feeds = feeds;
        this.context = context;
        imageLoader = new ImageLoader(context);
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
//            new DownloadImageTask((ImageView) v.findViewById(R.id.home_ivAvatar)).execute(feed.getAvatar());
            imageLoader.DisplayImage(feed.getAvatar(), home_ivAvatar);
            home_tvFeed.setText(getTittle(feed.getTitle()));
            home_tvUserName.setText(feed.getUsername());
            home_tvLike.setText("likes:" + feed.getLikes());
            home_tvComment.setText("comments:" + feed.getComments());
            home_tvDays.setText(String.valueOf(feed.getViewed()));
        }
        return v;
    }

    private String getTittle(String tittle)
    {
        try
        {
            tittle = tittle.substring(0, 15) + "..";
        }
        catch (Exception e)
        {
        }
        return tittle;
    }

    private void setUpViewFindByID(View v)
    {
        home_tvFeed = (TextView) v.findViewById(R.id.home_tvSoundTitle);
        home_tvUserName = (TextView) v.findViewById(R.id.home_tvUserName);
        home_tvLike = (TextView) v.findViewById(R.id.home_tvLike);
        home_tvComment = (TextView) v.findViewById(R.id.home_tvNumberComment);
        home_tvDays = (TextView) v.findViewById(R.id.home_tvDays);
        home_ivAvatar = (ImageView) v.findViewById(R.id.home_ivAvatar);
    }
}
