package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: anhnt
 * Date: 11/15/13
 * Time: 10:37 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonProfileResponse
{
    @JsonProperty("data")
    Profile profile;

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }
}
