package com.qsoft.ondio.customui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.lazzyload.ImageLoader;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/7/13
 * Time: 8:48 AM
 */
public class FeedArrayAdapter
{
    public static SimpleCursorAdapter getSimpleCursorAdapter(Context context, Cursor homeCursor)
    {
        final ImageLoader imageLoader = new ImageLoader(context);
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(context, R.layout.home_listfeeds,
                homeCursor, new String[]{
                Constants.FEED_COMMENTS,
                Constants.FEED_LIKES,
                Constants.FEED_USERNAME,
                Constants.FEED_TITLE,
                Constants.FEED_AVATAR,
                Constants.FEED_VIEWED},
                new int[]{
                        R.id.home_tvNumberComment,
                        R.id.home_tvLike,
                        R.id.home_tvUserName,
                        R.id.home_tvSoundTitle,
                        R.id.home_ivAvatar,
                        R.id.home_tvDays});

        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder()
        {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i)
            {
                switch (i)
                {
                    case 17:
                        if (cursor.getString(17) != null)
                        {
                            imageLoader.DisplayImage(cursor.getString(17), (ImageView) view.findViewById(R.id.home_ivAvatar));
                        }
                        break;
                    case 14:
                        ((TextView) view.findViewById(R.id.home_tvNumberComment)).setText("comments:" + cursor.getString(14));
                        break;
                    case 12:
                        ((TextView) view.findViewById(R.id.home_tvLike)).setText("likes:" + cursor.getString(12));
                        break;
                    case 15:
                        ((TextView) view.findViewById(R.id.home_tvUserName)).setText(cursor.getString(15));
                        break;
                    case 4:
                        ((TextView) view.findViewById(R.id.home_tvSoundTitle)).setText(getTitle(cursor.getString(4)));
                        break;
                    case 13:
                        ((TextView) view.findViewById(R.id.home_tvDays)).setText(cursor.getString(13));
                        break;
                }
                return true;
            }
        };
        mAdapter.setViewBinder(viewBinder);
        return mAdapter;
    }

    private static String getTitle(String title)
    {
        try
        {
            title = title.substring(0, 15) + "..";
        }
        catch (Exception e)
        {
        }
        return title;
    }
}
