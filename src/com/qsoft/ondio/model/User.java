package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.qsoft.ondio.util.Common;

import java.io.Serializable;

/**
 * Created by Udini on 6/26/13.
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
        values.put(Common.USER_ID, id);
        values.put(Common.USER_ACCESS_TOKEN, access_token);
        values.put(Common.USER_CLIENT_ID, client_id);
        values.put(Common.USER_USER_ID, user_id);
        values.put(Common.USER_EXPIRES, expires);
        values.put(Common.USER_SCOPE, scope);
        return values;
    }
}
