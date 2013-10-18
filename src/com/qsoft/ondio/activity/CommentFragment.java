package com.qsoft.ondio.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
    private ListView listComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.comments, null);
        findViewById(view);
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        setUpDataToCommentList(getActivity(), commentList);
        return view;
    }

    private void findViewById(View view)
    {
        listComment = (ListView) view.findViewById(R.id.comment_lvListComment);
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
        listComment.setAdapter(new ArrayAdapterComment(context, R.id.comment_lvListComment, commentList));
    }
}
