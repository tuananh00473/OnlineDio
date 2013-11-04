package com.qsoft.ondio.model;

/**
 * User: AnhNT
 * Date: 10/18/13
 * Time: 10:04 AM
 */
public class Comment
{
    private String account;
    private String comments;
    private String timeComment;

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getTimeComment()
    {
        return timeComment;
    }

    public void setTimeComment(String timeComment)
    {
        this.timeComment = timeComment;
    }

    public Comment(String account, String comments, String timeComment)
    {
        this.account = account;
        this.comments = comments;
        this.timeComment = timeComment;
    }
}
