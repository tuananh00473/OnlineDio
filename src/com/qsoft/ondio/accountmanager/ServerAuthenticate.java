package com.qsoft.ondio.accountmanager;

import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.model.jsonResult;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public User login(final String user, final String pass, String authType) throws Exception;

    public jsonResult updateProfile(Profile profile) throws Exception;

}
