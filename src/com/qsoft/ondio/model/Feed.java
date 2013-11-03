package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.qsoft.ondio.util.Common;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/12/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Feed
{
    public int id;
    public String user_id;
    public String title;
    public String thumbnail;
    public String sound_path;
    public String description;
    public String duration;
    public String played;
    public String created_at;
    public String updated_at;
    public String likes;
    public String viewed;
    public String comments;
    public String username;
    public String display_name;
    public String avatar;

    public Feed()
    {
    }

    public Feed(int id, String user_id, String title, String thumbnail, String sound_path, String description, String duration, String played, String created_at, String updated_at, String likes, String viewed, String comments, String username, String display_name, String avatar)
    {
        this.id = id;
        this.user_id = user_id;
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

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
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

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getPlayed()
    {
        return played;
    }

    public void setPlayed(String played)
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

    public String getLikes()
    {
        return likes;
    }

    public void setLikes(String likes)
    {
        this.likes = likes;
    }

    public String getViewed()
    {
        return viewed;
    }

    public void setViewed(String viewed)
    {
        this.viewed = viewed;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
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
        values.put(Common.FEED_ID, id);
        values.put(Common.FEED_USER_ID, user_id);
        values.put(Common.FEED_TITLE, title);
        values.put(Common.FEED_THUMBNAIL, thumbnail);
        values.put(Common.FEED_SOUND_PATH, sound_path);
        values.put(Common.FEED_DESCRIPTION, description);
        values.put(Common.FEED_DURATION, duration);
        values.put(Common.FEED_PLAYED, played);
        values.put(Common.FEED_CREATED_AT, created_at);
        values.put(Common.FEED_UPDATED_AT, updated_at);
        values.put(Common.FEED_LIKES, likes);
        values.put(Common.FEED_VIEWED, viewed);
        values.put(Common.FEED_COMMENTS, comments);
        values.put(Common.FEED_USERNAME, username);
        values.put(Common.FEED_DISPLAY_NAME, display_name);
        values.put(Common.FEED_AVATAR, avatar);
        return values;
    }
}
