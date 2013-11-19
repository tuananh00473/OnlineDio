package com.qsoft.ondio.controller;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.restservice.AccountShared;
import com.qsoft.ondio.restservice.Interceptor;
import com.qsoft.ondio.restservice.MyRestService;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: anhnt
 * Date: 11/19/13
 * Time: 11:45 AM
 */

@EBean
public class ProfileController
{
    @RestService
    MyRestService services;

    @Bean
    AccountShared accountShared;

    @Bean
    Interceptor interceptor;

    @AfterInject
    public void init()
    {
        if (accountShared.getUser_id() != null)
        {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(interceptor);
            services.getRestTemplate().setInterceptors(interceptors);
        }
    }

    public void updateProfile(String userId, Profile profile)
    {
        HashMap<String, Serializable> urlVariables = new HashMap<String, Serializable>();
        urlVariables.put("display_name", profile.getDisplay_name());
        urlVariables.put("full_name", profile.getFull_name());
        urlVariables.put("phone", profile.getPhone());
        urlVariables.put("birthday", profile.getBirthday());
        urlVariables.put("gender", profile.getGender());
        urlVariables.put("country_id", profile.getCountry_id());
        urlVariables.put("description", profile.getDescription());
        services.updateProfile(userId, urlVariables);
    }

    public Profile getProfile(String userId)
    {
        return services.getProfile(userId).getProfile();
    }
}
