package com.qsoft.ondio.restservice;

import com.googlecode.androidannotations.annotations.rest.*;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.ondio.model.JsonFeedResponse;
import com.qsoft.ondio.model.JsonProfileResponse;
import com.qsoft.ondio.model.User;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * User: anhnt
 * Date: 11/15/13
 * Time: 8:52 AM
 */
@Rest(rootUrl = "http://192.168.1.222/testing/ica467/trunk/public", converters = {MappingJackson2HttpMessageConverter.class})
public interface MyRestService
{
    @Get("/home-rest")
    @Accept(MediaType.APPLICATION_JSON)
    public JsonFeedResponse getHomeFeed();

    @Post("/auth-rest")
    @Accept(MediaType.APPLICATION_JSON)
    public User login(HashMap user);

    @Get("/user-rest/{account_id}")
    @Accept(MediaType.APPLICATION_JSON)
    public JsonProfileResponse getProfile(String account_id);

    @Put("/user-rest/{account_id}")
    public void updateProfile(String account_id, HashMap profile);

    public RestTemplate getRestTemplate();
}
