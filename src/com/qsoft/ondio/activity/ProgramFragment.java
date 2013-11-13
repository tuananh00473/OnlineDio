package com.qsoft.ondio.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RadioGroup;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.ondio.R;

/**
 * User: thinhdd
 * Date: 10/17/13
 * Time: 3:25 PM
 */

@EFragment(R.layout.program)
public class ProgramFragment extends Fragment
{
    private static final String TAG = "ProgramFragment";

    @ViewById(R.id.program_rgSelectInfo)
    RadioGroup rgInfo;

    @ViewById(R.id.program_btBack)
    Button btBack;

    @AfterViews
    void doShowThumbnail()
    {
        setUpListenerController();
        showFragment(new ThumbnailFragment_(), "ThumbnailFragment");
    }

    private void setUpListenerController()
    {
        rgInfo.setOnCheckedChangeListener(onCheckChangeListener);
    }

    private RadioGroup.OnCheckedChangeListener onCheckChangeListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch (checkedId)
            {
                case R.id.program_rbThumbnail:
                    doShowThumbnail();
                    break;
                case R.id.program_rbDetail:
                    doShowDetail();
                    break;
                case R.id.program_rbComments:
                    doShowComment();
                    break;
            }
        }
    };

    private void doShowDetail()
    {
        showFragment(new DetailFragment_(), "DetailFragment");
    }

    private void doShowComment()
    {
        showFragment(new CommentFragment_(), "CommentFragment");
    }

    private void showFragment(Fragment fragment, String tittle)
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.program_flInformation, fragment, tittle);
        ft.commit();
    }

    @Click(R.id.program_btBack)
    void doBack()
    {
        getFragmentManager().popBackStack();
    }
}
