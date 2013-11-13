package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.ondio.R;
import com.qsoft.ondio.customui.ArrayAdapterComment;
import com.qsoft.ondio.model.Comment;

import java.util.ArrayList;

/**
 * User: thinhdd
 * Date: 10/18/13
 * Time: 10:03 AM
 */

@EFragment(R.layout.comments)
public class CommentFragment extends Fragment
{
    private static final String TAG = "CommentFragment";
    private static final int REQUEST_CODE_RETURN_COMMENT = 777;

    @ViewById(R.id.comment_lvListComment)
    ListView lvComment;

    private ArrayList<Comment> commentList;

    @AfterViews
    public void afterViews()
    {
        setUpDataToCommentList();
    }

    @Click(R.id.comments_tvInputComment)
    void setActionInputComment()
    {
        doShowInputCommentActivity();
    }

    private void doShowInputCommentActivity()
    {
        getActivity().startActivityForResult(new Intent(getActivity(), InputCommentActivity_.class), REQUEST_CODE_RETURN_COMMENT);
    }

    private void setUpDataToCommentList()
    {
        commentList = new ArrayList<Comment>();
        Comment comment0 = new Comment("Kim Dong Ho", "askldfnaslkdfnal;dfnaldkfnaskldfnalksdfnl;asdfnlaksdfakl;dfal;kfhl;aksfhlks", "3 phut truoc");
        Comment comment1 = new Comment("Kim Dong Ho", "nds,adnfl", "3 phut truoc");
        Comment comment2 = new Comment("Kim Dong Ho", "be len ba be di mau giao co thuong be vi be khong khoc nhe", "3 phut truoc");
        Comment comment3 = new Comment("Kim Dong Ho", "mot con vit xoe ra hai cai canh", "3 phut truoc");
        Comment comment4 = new Comment("Kim Dong Ho", "mot voi mot la hai hai them hai la bon bon voi mot la nam nam ngon tay sach deu nam anh em tren mot chiec xe tang nhu nam bong hoa no cung mot coi nhu nam ngon tay tren mot ban tay da ra tran la nam nguoi nhu mot ah ah ah ag", "3 phut truoc");
        commentList.add(comment0);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        updateCommentList(commentList);
    }

    private void updateCommentList(ArrayList<Comment> commentList)
    {
        lvComment.setAdapter(new ArrayAdapterComment(getActivity(), R.id.comment_lvListComment, commentList));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (requestCode == REQUEST_CODE_RETURN_COMMENT && resultCode == Activity.RESULT_OK && null != data)
        {
            Comment comment = new Comment("Kim Dong Ho", data.getStringExtra("data"), "3 giay truoc");
            commentList.add(comment);
            updateCommentList(commentList);
        }
    }
}
