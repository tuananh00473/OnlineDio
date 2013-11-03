package com.qsoft.ondio.accountmanager;

import com.qsoft.ondio.model.JsonResult;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public User login(final String user, final String pass, String authType) throws Exception;

    public JsonResult updateProfile(Profile profile);

    public JsonResult getHomeFeed(int limit, int offset, String dateFrom, String dateTo);

}
