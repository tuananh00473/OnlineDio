package com.qsoft.ondio.model;

import android.content.ContentValues;
import com.qsoft.ondio.util.Common;

import java.io.Serializable;

/**
 * User: anhnt
 * Date: 10/18/13
 * Time: 4:01 PM
 */
public class Profile implements Serializable
{
    private long id;
    private String displayName;
    private String fullName;
    private String phoneNo;
    private String birthday;
    private int gender;
    private String country;
    private String description;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
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

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(Common.PROFILE_ID, id);
        values.put(Common.PROFILE_DISPLAY_NAME, displayName);
        values.put(Common.PROFILE_FULL_NAME, fullName);
        values.put(Common.PROFILE_PHONE, phoneNo);
        values.put(Common.PROFILE_BIRTHDAY, birthday);
        values.put(Common.PROFILE_GENDER, gender);
        values.put(Common.PROFILE_COUNTRY, country);
        values.put(Common.PROFILE_DESCRIPTION, description);
        return values;
    }
}
