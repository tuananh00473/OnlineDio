package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.qsoft.ondio.util.Constants;

import java.io.Serializable;

/**
 * User: AnhNT
 * Date: 10/18/13
 * Time: 4:01 PM
 */
public class Profile implements Serializable
{
    private int id;
    private int facebook_id;
    private String username;
    private String password;
    private String avatar;
    private String cover_image;
    private String display_name;
    private String full_name;
    private String phone;
    private String birthday;
    private int gender;
    private String country_id;
    private int storage_plan_id;
    private String description;
    private String created_at;
    private String updated_at;
    private int sounds;
    private int favorites;
    private int likes;
    private int followings;
    private int audiences;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getFacebook_id()
    {
        return facebook_id;
    }

    public void setFacebook_id(int facebook_id)
    {
        this.facebook_id = facebook_id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getCover_image()
    {
        return cover_image;
    }

    public void setCover_image(String cover_image)
    {
        this.cover_image = cover_image;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getCountry_id()
    {
        return country_id;
    }

    public void setCountry_id(String country_id)
    {
        this.country_id = country_id;
    }

    public int getStorage_plan_id()
    {
        return storage_plan_id;
    }

    public void setStorage_plan_id(int storage_plan_id)
    {
        this.storage_plan_id = storage_plan_id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(Constants.PROFILE_ID, id);
        values.put(Constants.PROFILE_AVATAR, avatar);
        values.put(Constants.PROFILE_COVER_IMAGE, cover_image);
        values.put(Constants.PROFILE_DISPLAY_NAME, display_name);
        values.put(Constants.PROFILE_FULL_NAME, full_name);
        values.put(Constants.PROFILE_PHONE, phone);
        values.put(Constants.PROFILE_BIRTHDAY, birthday);
        values.put(Constants.PROFILE_GENDER, gender);
        values.put(Constants.PROFILE_COUNTRY, country_id);
        values.put(Constants.PROFILE_DESCRIPTION, description);
        return values;
    }
}
