package com.qsoft.ondio.accountmanager;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qsoft.ondio.model.JsonResult;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.StringConverter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URL;

/**
 * User: anhnt
 * Date: 10/12/13
 * Time: 11:21 AM
 */

public class ParseComServer implements ServerAuthenticate
{
    private static final String TAG = "ParseComServer";

    @Override
    public User login(String user, String pass, String authType) throws Exception
    {
        Log.i(TAG, "before login");
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing/";
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        pass = new StringConverter().doConvert(pass);
        Log.i(TAG, "login1");
        try
        {
            URL realUrl = new URL(url);
            Log.i(TAG, "login URL");
            HttpPost httpPost = new HttpPost(realUrl.toURI());
            Log.i(TAG, "login URI");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", user);
            jsonObject.addProperty("password", pass);
            jsonObject.addProperty("grant_type", "password");
            jsonObject.addProperty("client_id", "123456789");

            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            return new Gson().fromJson(responseString, User.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResult updateProfile(Profile profile)
    {
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/" + profile.getId();
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing/" + profile.getId();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest/" + profile.getId();
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
            URL realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", profile.getId());
            jsonObject.addProperty("display_name", profile.getDisplayName());
            jsonObject.addProperty("full_name", profile.getFullName());
            jsonObject.addProperty("phone", profile.getPhoneNo());
            jsonObject.addProperty("birthday", profile.getBirthday());
            jsonObject.addProperty("gender", profile.getGender());
            jsonObject.addProperty("country_id", profile.getCountry());
            jsonObject.addProperty("description", profile.getDescription());

            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            return new Gson().fromJson(responseString, JsonResult.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonResult getHomeFeed(int limit, int offset, String dateFrom, String dateTo)
    {
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest";
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing";
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
            URL realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("limit", limit);
            jsonObject.addProperty("offset", offset);
            jsonObject.addProperty("time_from", dateFrom);
            jsonObject.addProperty("time_to", dateTo);

            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            return new Gson().fromJson(responseString, JsonResult.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
