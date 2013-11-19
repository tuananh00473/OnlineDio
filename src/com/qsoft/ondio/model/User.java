package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qsoft.ondio.util.Constants;

import java.io.Serializable;

/**
 * User: AnhNT
 * Date: 10/18/13
 * Time: 4:01 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable
{
    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("client_id")
    private String client_id;

    @JsonProperty("user_id")
    private String user_id;

    @JsonProperty("expires")
    private String expires;

    @JsonProperty("scope")
    private String scope;

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
        values.put(Constants.USER_ACCESS_TOKEN, access_token);
        values.put(Constants.USER_CLIENT_ID, client_id);
        values.put(Constants.USER_USER_ID, user_id);
        values.put(Constants.USER_EXPIRES, expires);
        values.put(Constants.USER_SCOPE, scope);
        return values;
    }
}
