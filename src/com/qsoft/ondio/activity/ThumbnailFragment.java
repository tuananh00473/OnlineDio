package com.qsoft.ondio.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.ondio.R;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/18/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ThumbnailFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.thumbnail, null);
        return view;
    }
}
