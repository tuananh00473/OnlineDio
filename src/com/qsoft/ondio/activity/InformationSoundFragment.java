package com.qsoft.ondio.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.ondio.R;

public class InformationSoundFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.infomation_sound, null);
        setUpViewHome(view);
        return view;
    }

    public void setUpViewHome(View upViewHome)
    {

    }
}
