package com.qsoft.ondio.accountmanager;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public User userSignIn(final String user, final String pass, String authType) throws Exception;
}
