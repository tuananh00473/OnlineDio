package com.qsoft.ondio.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.ondio.util.Constants;

/**
 * User: AnhNT
 * Date: 10/12/13
 * Time: 10:48 AM
 */
public class Feed
{
    public int id;
    public int user_id;
    public int account_id;
    public String title;
    public String thumbnail;
    public String sound_path;
    public String description;
    public int duration;
    public int played;
    public String created_at;
    public String updated_at;
    public int likes;
    public int viewed;
    public int comments;
    public String username;
    public String display_name;
    public String avatar;

    public Feed()
    {
    }

    public Feed(int id, int user_id, int account_id, String title, String thumbnail, String sound_path, String description, int duration, int played, String created_at, String updated_at, int likes, int viewed, int comments, String username, String display_name, String avatar)
    {
        this.id = id;
        this.user_id = user_id;
        this.account_id = account_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.sound_path = sound_path;
        this.description = description;
        this.duration = duration;
        this.played = played;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.likes = likes;
        this.viewed = viewed;
        this.comments = comments;
        this.username = username;
        this.display_name = display_name;
        this.avatar = avatar;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public int getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(int account_id)
    {
        this.account_id = account_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getSound_path()
    {
        return sound_path;
    }

    public void setSound_path(String sound_path)
    {
        this.sound_path = sound_path;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getPlayed()
    {
        return played;
    }

    public void setPlayed(int played)
    {
        this.played = played;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getViewed()
    {
        return viewed;
    }

    public void setViewed(int viewed)
    {
        this.viewed = viewed;
    }

    public int getComments()
    {
        return comments;
    }

    public void setComments(int comments)
    {
        this.comments = comments;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(Constants.FEED_ID, id);
        values.put(Constants.FEED_USER_ID, user_id);
        values.put(Constants.FEED_TITLE, title);
        values.put(Constants.FEED_THUMBNAIL, thumbnail);
        values.put(Constants.FEED_SOUND_PATH, sound_path);
        values.put(Constants.FEED_DESCRIPTION, description);
        values.put(Constants.FEED_DURATION, duration);
        values.put(Constants.FEED_PLAYED, played);
        values.put(Constants.FEED_CREATED_AT, created_at);
        values.put(Constants.FEED_UPDATED_AT, updated_at);
        values.put(Constants.FEED_LIKES, likes);
        values.put(Constants.FEED_VIEWED, viewed);
        values.put(Constants.FEED_COMMENTS, comments);
        values.put(Constants.FEED_USERNAME, username);
        values.put(Constants.FEED_DISPLAY_NAME, display_name);
        values.put(Constants.FEED_AVATAR, avatar);
        return values;
    }

    public static Feed fromCursor(Cursor c)
    {
        int id = c.getInt(c.getColumnIndex(Constants.FEED_ID));
        int user_id = c.getInt(c.getColumnIndex(Constants.FEED_USER_ID));
        int account_id = c.getInt(c.getColumnIndex(Constants.FEED_ACCOUNT_ID));
        String title = c.getString(c.getColumnIndex(Constants.FEED_TITLE));
        String thumbnail = c.getString(c.getColumnIndex(Constants.FEED_THUMBNAIL));
        String sound_path = c.getString(c.getColumnIndex(Constants.FEED_SOUND_PATH));
        String description = c.getString(c.getColumnIndex(Constants.FEED_DESCRIPTION));
        int duration = c.getInt(c.getColumnIndex(Constants.FEED_DURATION));
        int played = c.getInt(c.getColumnIndex(Constants.FEED_PLAYED));
        String created_at = c.getString(c.getColumnIndex(Constants.FEED_CREATED_AT));
        String updated_at = c.getString(c.getColumnIndex(Constants.FEED_UPDATED_AT));
        int likes = c.getInt(c.getColumnIndex(Constants.FEED_LIKES));
        int viewed = c.getInt(c.getColumnIndex(Constants.FEED_VIEWED));
        int comments = c.getInt(c.getColumnIndex(Constants.FEED_COMMENTS));
        String username = c.getString(c.getColumnIndex(Constants.FEED_USERNAME));
        String display_name = c.getString(c.getColumnIndex(Constants.FEED_DISPLAY_NAME));
        String avatar = c.getString(c.getColumnIndex(Constants.FEED_AVATAR));
        return new Feed(id, user_id, account_id, title, thumbnail, sound_path, description, duration, played, created_at, updated_at, likes, viewed, comments, username, display_name, avatar);
    }

    @Override
    public int hashCode()
    {
        int result = display_name.hashCode();
        result = 31 * result;
        return result;
    }
}
