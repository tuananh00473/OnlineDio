package com.qsoft.ondio.util;

import android.net.Uri;
import com.qsoft.ondio.accountmanager.ParseComServer;
import com.qsoft.ondio.accountmanager.ServerAuthenticate;

/**
 * User: AnhNT
 * Date: 10/30/13
 * Time: 8:53 AM
 */

public class Constants
{
    //Request code
    public static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    public static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    public static final int REQUEST_CODE_RETURN_COMMENT = 777;

    //    authentication constants
    public final static String ARG_ACCOUNT_TYPE = "com.qsoft.onlinedio";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";
    public static final ServerAuthenticate sServerAuthenticate = new ParseComServer();

    public static final String USERDATA_USER_OBJ_ID = "userObjectId";   //Parse.com object id
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    //    profile constants
    public static final String PROFILE_ID = "id";
    public static final String PROFILE_AVATAR = "avatar";
    public static final String PROFILE_COVER_IMAGE = "cover_image";
    public static final String PROFILE_DISPLAY_NAME = "displayName";
    public static final String PROFILE_FULL_NAME = "fullName";
    public static final String PROFILE_PHONE = "phoneNo";
    public static final String PROFILE_BIRTHDAY = "birthday";
    public static final String PROFILE_GENDER = "gender";
    public static final String PROFILE_COUNTRY = "country";
    public static final String PROFILE_DESCRIPTION = "description";

    //    database
    public static final String DATABASE_NAME = "localdata.db";
    public static final int DATABASE_VERSION = 3;
    public static final String PROVIDER_NAME = "com.qsoft.ondio.localDB";

    //    database profile constants
    public static final String PROFILE_TABLE_NAME = "profiles";
    public static final String URL_PROFILE = "content://" + PROVIDER_NAME + "/" + PROFILE_TABLE_NAME;
    public static final Uri CONTENT_URI_PROFILE = Uri.parse(URL_PROFILE);

    //    database feed contants
    public static final String FEED_TABLE_NAME = "feeds";
    public static final String URL_FEED = "content://" + PROVIDER_NAME + "/" + FEED_TABLE_NAME;
    public static final Uri CONTENT_URI_FEED = Uri.parse(URL_FEED);

    //    database user constants
    public static final String USER_TABLE_NAME = "users";
    public static final String URL_USER = "content://" + PROVIDER_NAME + "/" + USER_TABLE_NAME;
    public static final Uri CONTENT_URI_USER = Uri.parse(URL_USER);

    //    home feed constants
    public static final String FEED_ID = "id";
    public static final String FEED_USER_ID = "user_id";
    public static final String FEED_ACCOUNT_ID = "account_id";
    public static final String FEED_TITLE = "title";
    public static final String FEED_THUMBNAIL = "thumbnail";
    public static final String FEED_DESCRIPTION = "description";
    public static final String FEED_SOUND_PATH = "sound_path";
    public static final String FEED_DURATION = "duration";
    public static final String FEED_PLAYED = "played";
    public static final String FEED_CREATED_AT = "created_at";
    public static final String FEED_UPDATED_AT = "update_at";
    public static final String FEED_LIKES = "likes";
    public static final String FEED_VIEWED = "viewed";
    public static final String FEED_COMMENTS = "comments";
    public static final String FEED_USERNAME = "username";
    public static final String FEED_DISPLAY_NAME = "display_name";
    public static final String FEED_AVATAR = "avatar";

    //    user constants
    public static final String USER_ID = "id";
    public static final String USER_ACCESS_TOKEN = "access_token";
    public static final String USER_CLIENT_ID = "client_id";
    public static final String USER_USER_ID = "user_id";
    public static final String USER_EXPIRES = "expires";
    public static final String USER_SCOPE = "scope";
}
