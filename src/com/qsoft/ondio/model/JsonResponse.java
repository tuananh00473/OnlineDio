package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * User: anhnt
 * Date: 11/15/13
 * Time: 10:37 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResponse
{
    @JsonProperty("data")
    ArrayList<Feed> homeList;

    public ArrayList<Feed> getHomeList()
    {
        return homeList;
    }

    public void setHomeList(ArrayList<Feed> homeList)
    {
        this.homeList = homeList;
    }
}
