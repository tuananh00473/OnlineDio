package com.qsoft.ondio.controller;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.restservice.AccountShared;
import com.qsoft.ondio.restservice.Interceptor;
import com.qsoft.ondio.restservice.MyRestService;
import com.qsoft.ondio.util.StringConverter;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: anhnt
 * Date: 11/19/13
 * Time: 9:39 AM
 */
@EBean
public class LoginController
{
    @Bean
    Interceptor interceptor;

    @Bean
    AccountShared accountShared;

    @RestService
    MyRestService services;

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

    public User login(String username, String password)
    {
        try
        {
            password = new StringConverter().doConvert(password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        HashMap<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("username", username);
        urlVariables.put("password", password);
        urlVariables.put("grant_type", "password");
        urlVariables.put("client_id", "123456789");
        return services.login(urlVariables);
    }
}
