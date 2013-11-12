package com.qsoft.ondio.controller;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/11/13
 * Time: 3:54 PM
 */

@EBean
public class DatabaseController
{
    private static final String TAG = "DatabaseController";

    @RootContext
    Activity activity;

    public void saveUserToDB(ContentValues values)
    {
        Cursor c = activity.managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
        if (c.moveToFirst())
        {
            int k = activity.getContentResolver().update(Constants.CONTENT_URI_USER, values, null, null);
            if (0 != k)
            {
                Log.d(TAG, "updated user.");
            }
        }
        else
        {
            Uri uri = activity.getContentResolver().insert(Constants.CONTENT_URI_USER, values);
            if (null != uri)
            {
                Log.d(TAG, "inserted user.");
            }
        }
    }

    public User loadUserFromDB()
    {
        Cursor c = activity.managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
        if (null != c && c.moveToFirst())
        {
            User user = new User();
            user.setId(c.getInt(c.getColumnIndex(Constants.USER_ID)));
            user.setAccess_token(c.getString(c.getColumnIndex(Constants.USER_ACCESS_TOKEN)));
            user.setClient_id(c.getString(c.getColumnIndex(Constants.USER_CLIENT_ID)));
            user.setUser_id(c.getString(c.getColumnIndex(Constants.USER_USER_ID)));
            user.setExpires(c.getString(c.getColumnIndex(Constants.USER_EXPIRES)));
            user.setScope(c.getString(c.getColumnIndex(Constants.USER_SCOPE)));
            return user;
        }
        return null;
    }

    public Profile loadProfileFromDB(User user)
    {
        Log.d(TAG, "user : " + user);
        Uri uri = ContentUris.withAppendedId(Constants.CONTENT_URI_PROFILE, Integer.parseInt(user.getUser_id()));
        Cursor c = activity.managedQuery(uri, null, null, null, "_id");
        if (null != c && c.moveToFirst())
        {
            Profile profile = new Profile();

            profile.setDisplay_name(c.getString(c.getColumnIndex(Constants.PROFILE_DISPLAY_NAME)));
            profile.setFull_name(c.getString(c.getColumnIndex(Constants.PROFILE_FULL_NAME)));
            profile.setPhone(c.getString(c.getColumnIndex(Constants.PROFILE_PHONE)));
            profile.setBirthday(c.getString(c.getColumnIndex(Constants.PROFILE_BIRTHDAY)));
            profile.setGender(c.getInt(c.getColumnIndex(Constants.PROFILE_GENDER)));
            profile.setCountry_id(c.getString(c.getColumnIndex(Constants.PROFILE_COUNTRY)));
            profile.setDescription(c.getString(c.getColumnIndex(Constants.PROFILE_DESCRIPTION)));
            profile.setAvatar(c.getString(c.getColumnIndex(Constants.PROFILE_AVATAR)));
            profile.setCover_image(c.getString(c.getColumnIndex(Constants.PROFILE_COVER_IMAGE)));

            return profile;
        }
        return null;
    }

    public void saveProfileToDB(Profile profile)
    {
        Uri uri = ContentUris.withAppendedId(Constants.CONTENT_URI_PROFILE, profile.getId());
        Cursor c = activity.managedQuery(uri, null, null, null, "_id");
        if (c.moveToFirst())
        {
            // get profile lan dau thi server tra ve full link cua image avatar va image cover
            // sau khi update profile thi server chi tra ve ten cua image
            // the nen phai lam them cai doan nay :( ==> rat cu chuoi!
            String linkAvatar = c.getString(c.getColumnIndex(Constants.PROFILE_AVATAR));
            if (null != linkAvatar && null != profile.getAvatar() && linkAvatar.contains(profile.getAvatar()))
            {
                profile.setAvatar(linkAvatar);
            }
            String linkCoverImage = c.getString(c.getColumnIndex(Constants.PROFILE_COVER_IMAGE));
            if (null != linkCoverImage && null != profile.getCover_image() && linkCoverImage.contains(profile.getCover_image()))
            {
                profile.setCover_image(linkCoverImage);
            }

            activity.getContentResolver().update(Constants.CONTENT_URI_PROFILE, profile.getContentValues(), null, null);
        }
        else
        {
            ContentValues values = profile.getContentValues();
            activity.getContentResolver().insert(Constants.CONTENT_URI_PROFILE, values);
        }
    }

}
