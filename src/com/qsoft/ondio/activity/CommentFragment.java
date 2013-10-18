package com.qsoft.ondio.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterComment;
import com.qsoft.ondio.model.Comment;

import java.util.ArrayList;

/**
 * User: thinhdd
 * Date: 10/18/13
 * Time: 10:03 AM
 */
public class CommentFragment extends Fragment
{
    private ListView lvComment;
    private TextView tvInputComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.comments, null);
        findViewById(view);
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        setUpDataToCommentList(getActivity(), commentList);
        setListenerController();
        return view;
    }

    private void setListenerController()
    {
        tvInputComment.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.comments_tvInputComment:
                    doShowInputCommentActivity();
            }
        }
    };

    private void doShowInputCommentActivity()
    {
        startActivity(new Intent(getActivity(), InputCommentActivity.class));
    }

    private void findViewById(View view)
    {
        lvComment = (ListView) view.findViewById(R.id.comment_lvListComment);
        tvInputComment = (TextView) view.findViewById(R.id.comments_tvInputComment);
    }

    private void setUpDataToCommentList(Context context, ArrayList<Comment> commentList)
    {
        Comment comment = new Comment("Kim Dong Ho", "Nghe nhu Shit", "3 phut truoc");
        Comment comment1 = new Comment("Kim Dong Ho", "Nghe nhu Shit", "3 phut truoc");
        Comment comment2 = new Comment("Kim Dong Ho", "Nghe nhu Shit", "3 phut truoc");
        Comment comment3 = new Comment("Kim Dong Ho", "Nghe nhu Shit", "3 phut truoc");
        Comment comment4 = new Comment("Kim Dong Ho", "Nghe nhu Shit", "3 phut truoc");
        commentList.add(comment);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        lvComment.setAdapter(new ArrayAdapterComment(context, R.id.comment_lvListComment, commentList));
    }
}
