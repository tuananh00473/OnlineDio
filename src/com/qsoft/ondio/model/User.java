package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.qsoft.ondio.util.Constants;

import java.io.Serializable;

/**
 * User: AnhNT
 * Date: 10/18/13
 * Time: 4:01 PM
 */
public class User implements Serializable
{
    private int id;
    private String access_token;
    private String client_id;
    private String user_id;
    private String expires;
    private String scope;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    public String getClient_id()
    {
        return client_id;
    }

    public void setClient_id(String client_id)
    {
        this.client_id = client_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getExpires()
    {
        return expires;
    }

    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(Constants.USER_ID, id);
        values.put(Constants.USER_ACCESS_TOKEN, access_token);
        values.put(Constants.USER_CLIENT_ID, client_id);
        values.put(Constants.USER_USER_ID, user_id);
        values.put(Constants.USER_EXPIRES, expires);
        values.put(Constants.USER_SCOPE, scope);
        return values;
    }
}
