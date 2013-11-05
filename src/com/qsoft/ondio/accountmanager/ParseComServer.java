package com.qsoft.ondio.accountmanager;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qsoft.ondio.model.Feed;
import com.qsoft.ondio.model.JsonResult;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.StringConverter;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: anhnt
 * Date: 10/12/13
 * Time: 11:21 AM
 */

public class ParseComServer implements ServerAuthenticate
{
    private static final String TAG = "ParseComServer";
    private final static String APP_ID = "iRnc8I1X0du5q6HrJtZW0a5DlB0JcpOQbjA6chha";
    private final static String REST_API_KEY = "tv1xCdYKTwI3p205KHCn1yWpbVj2OHldV9cPZuNZ";

    @Override
    public User login(String user, String pass, String authType) throws Exception
    {
        Log.i(TAG, "before login");
        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing/";
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";
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
        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/" + profile.getId();

//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing/" + profile.getId();
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + profile.getId();

        DefaultHttpClient httpClient = new DefaultHttpClient();

        try
        {
            URL realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("display_name", profile.getDisplay_name());
            jsonObject.addProperty("full_name", profile.getFull_name());
            jsonObject.addProperty("phone", profile.getPhone());
            jsonObject.addProperty("birthday", profile.getBirthday());
            jsonObject.addProperty("gender", "1");
            jsonObject.addProperty("country_id", "1");
            jsonObject.addProperty("description", profile.getDescription());

//            httpPost.setHeader("Content-type", "application/json");
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

    public String loadDataWithGetMethod(String url)
    {
        String result = null;
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity());
            return result;
        }
        catch (Exception e)
        {
            result = e.getMessage();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Feed> getHomeFeed(String authToken) throws JSONException
    {
        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest"
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest"
                + "?access_token="
                + authToken;
        String result = loadDataWithGetMethod(url);

        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonData = (JSONArray) jsonObject.get("data");
        ArrayList<Feed> homeFeedDTOList = new ArrayList<Feed>();
        for (int i = 0; i < jsonData.length(); i++)
        {
            homeFeedDTOList.add(new Gson().fromJson(jsonData.getString(i), Feed.class));
        }
        return homeFeedDTOList;
    }

    public static List<Header> getAppParseComHeaders()
    {
        List<Header> ret = new ArrayList<Header>();
        ret.add(new BasicHeader("X-Parse-Application-Id", APP_ID));
        ret.add(new BasicHeader("X-Parse-REST-API-Key", REST_API_KEY));
        return ret;
    }

    @Override
    public void putShow(String authToken, String userId, Feed remoteFeed) throws Exception
    {
        Log.d("udinic", "putShow [" + remoteFeed.display_name + "]");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/classes/tvshows";

        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders())
        {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("X-Parse-Session-Token", authToken); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires
        httpPost.addHeader("Content-Type", "application/json");

        JSONObject feedShow = new JSONObject();
        feedShow.put("id", remoteFeed.id);
        feedShow.put("user_id", remoteFeed.user_id);
        feedShow.put("title", remoteFeed.title);
        feedShow.put("thumbnail", remoteFeed.thumbnail);
        feedShow.put("sound_path", remoteFeed.sound_path);
        feedShow.put("description", remoteFeed.description);
        feedShow.put("duration", remoteFeed.duration);
        feedShow.put("played", remoteFeed.played);
        feedShow.put("created_at", remoteFeed.created_at);
        feedShow.put("updated_at", remoteFeed.updated_at);
        feedShow.put("likes", remoteFeed.likes);
        feedShow.put("viewed", remoteFeed.viewed);
        feedShow.put("comments", remoteFeed.comments);
        feedShow.put("username", remoteFeed.username);
        feedShow.put("display_name", remoteFeed.display_name);
        feedShow.put("avatar", remoteFeed.avatar);

        // Creating ACL JSON object for the current user
        JSONObject acl = new JSONObject();
        JSONObject aclEveryone = new JSONObject();
        JSONObject aclMe = new JSONObject();
        aclMe.put("read", true);
        aclMe.put("write", true);
        acl.put(userId, aclMe);
        acl.put("*", aclEveryone);
        feedShow.put("ACL", acl);

        String request = feedShow.toString();
        Log.d("udinic", "Request = " + request);
        httpPost.setEntity(new StringEntity(request, "UTF-8"));

        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 201)
            {
                ParseComServer.ParseComError error = new Gson().fromJson(responseString, ParseComServer.ParseComError.class);
                throw new Exception("Error posting feed [" + error.code + "] - " + error.error);
            }
            else
            {
//                Log.d("udini", "Response string = " + responseString);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }
}
