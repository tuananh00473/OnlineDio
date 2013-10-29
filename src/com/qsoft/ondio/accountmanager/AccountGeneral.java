package com.qsoft.ondio.accountmanager;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 20/03/13
 * Time: 18:11
 */
public class AccountGeneral
{

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.qsoft.auth_example";

    /**
     * Account name
     */

    /**
     * User data fields
     */
    public static final String USERDATA_USER_OBJ_ID = "userObjectId";   //Parse.com object id

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServer();
}
