package com.qsoft.ondio.provider;

import android.provider.BaseColumns;

/**
 * User: anhnt
 * Date: 10/31/13
 * Time: 11:20 AM
 */
public class FinchIFeed
{
    public static final class Feed implements BaseColumns
    {
        public Feed()
        {
        }

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String TITLE = "title";
        public static final String THUMBNAIL = "thumbnail";
        public static final String DESCRIPTION = "description";
        public static final String SOUND_PATH = "sound_path";
        public static final String DURATION = "duration";
        public static final String PLAYED = "played";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATE_AT = "update_at";
        public static final String LIKES = "likes";
        public static final String VIEWED = "viewed";
        public static final String COMMENTS = "comments";
        public static final String USERNAME = "username";
        public static final String DISPLAY_NAME = "display_name";
        public static final String AVATAR = "avatar";
    }
}
