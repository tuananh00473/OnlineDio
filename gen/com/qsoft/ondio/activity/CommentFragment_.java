//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import com.qsoft.ondio.R.layout;

public final class CommentFragment_
        extends CommentFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState)
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_()
    {
        lvComment = ((ListView) findViewById(com.qsoft.ondio.R.id.comment_lvListComment));
        {
            View view = findViewById(com.qsoft.ondio.R.id.comments_tvInputComment);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        CommentFragment_.this.setActionInputComment();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null)
        {
            contentView_ = inflater.inflate(layout.comments, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id)
    {
        if (contentView_ == null)
        {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static CommentFragment_.FragmentBuilder_ builder()
    {
        return new CommentFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_
    {

        private Bundle args_;

        private FragmentBuilder_()
        {
            args_ = new Bundle();
        }

        public CommentFragment build()
        {
            CommentFragment_ fragment_ = new CommentFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
