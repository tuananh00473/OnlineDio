package com.qsoft.ondio.restservice;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.ondio.util.Constants;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * User: anhnt
 * Date: 11/15/13
 * Time: 9:26 AM
 */
@EBean
public class Interceptor implements ClientHttpRequestInterceptor
{
    @SystemService
    AccountManager accountManager;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution) throws IOException
    {
        request.getHeaders().add("Content-type", "application/json");

        Account account = accountManager.getAccountsByType(Constants.ARG_ACCOUNT_TYPE)[0];
        String accessToken = accountManager.peekAuthToken(account, Constants.AUTHTOKEN_TYPE_FULL_ACCESS);
        request.getHeaders().add("Authorization", "Bearer " + accessToken);

        return execution.execute(request, data);
    }
}
