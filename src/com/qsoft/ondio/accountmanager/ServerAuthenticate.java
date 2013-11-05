package com.qsoft.ondio.accountmanager;

import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.model.JsonResult;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public User login(final String user, final String pass, String authType) throws Exception;

    public JsonResult updateProfile(Profile profile, String authToken);

    public ArrayList<Feed> getHomeFeed(String authToken) throws JSONException;

    public void putShow(String authToken, String userObjectId, Feed remoteFeed) throws Exception;

    public Profile getProfile(String userId, String authToken) throws JSONException;
}
