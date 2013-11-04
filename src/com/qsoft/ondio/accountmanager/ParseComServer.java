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
//        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest/" + profile.getId();

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

//    @Override
//    public JsonResult getHomeFeed(String authToken)
//    {
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest?"
//                + "?access_token=" + authToken;
////        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/service-testing";
////        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//
//        try
//        {
//            URL realUrl = new URL(url);
//            HttpPost httpPost = new HttpPost(realUrl.toURI());
//
//            JsonObject jsonObject = new JsonObject();
//
//            httpPost.setHeader("Authorization", "Bearer " + authToken);
//            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
//
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            String responseString = EntityUtils.toString(httpResponse.getEntity());
//            return new Gson().fromJson(responseString, JsonResult.class);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

//    public ArrayList<Feed> getHomeFeed(String authToken) throws IOException, JSONException
//    {
//
//        Log.i(TAG, "getHomeFeed . auth Token [" + authToken + "]");
//        ArrayList<Feed> homeFeeds = new ArrayList<Feed>();
//        // Get a httpclient to talk to the internet
//        HttpClient httpClient = new DefaultHttpClient();
//        // Perform a GET request to service for a JSON list of all the feeds
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest?"
//                + "limit=&offset=&time_from=&time_to=";
//        HttpUriRequest httpGet = new HttpGet(url);
//        authToken = "Bearer " + authToken;
//
//        // httpGet.addHeader("X-Parse-Session-Token", authToken);
//        httpGet.addHeader("Authorization", authToken);
//
//        // Get the response that service sends back
//        HttpResponse response = httpClient.execute(httpGet);
//        Log.i(TAG, "Response status :"
//                + response.getStatusLine().getStatusCode());
//        // Convert this response into a readable String
//        String jsonString = StreamUtils.convertToString(response.getEntity()
//                .getContent());
//        Log.i(TAG, jsonString);
//        // Create a JSON object that we can use from the String
//        JSONObject json = new JSONObject(jsonString);
//        Log.i(TAG, "Parsing...");
//
//        // Get all results items
//        JSONArray jsonArray = json.getJSONArray("data");
//        // Create a list to store all feeds
//        for (int i = 0; i < jsonArray.length(); i++)
//        {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            int id;
//            id = jsonObject.getInt("id");
//
//            int user_id;
//            user_id = jsonObject.getInt("user_id");
//
//            String title;
//            title = jsonObject.getString("title");
//
//            String thumbnail;
//            thumbnail = jsonObject.getString("thumbnail");
//
//            String description = jsonObject.getString("description");
//            String sound_path = jsonObject.getString("sound_path");
//            int duration = jsonObject.getInt("duration");
//            int played;
//            try
//            {
//                played = jsonObject.getInt("played");
//            }
//            catch (JSONException e)
//            {
//                played = 0;
//            }
//            String created_at = jsonObject.getString("created_at");
//            String updated_at = jsonObject.getString("updated_at");
//
//            int likes;
//            likes = jsonObject.getInt("likes");
//
//            int viewd;
//            viewd = jsonObject.getInt("viewed");
//
//            int comments;
//            comments = jsonObject.getInt("comments");
//            String username = jsonObject.getString("username");
//
//            String display_name;
//            display_name = jsonObject.getString("display_name");
//
//            String avatar = jsonObject.getString("avatar");
//            // Add a homefeed into list
//            homeFeeds.add(new Feed(id, user_id, title, thumbnail,
//                    description, sound_path, duration, played,
//                    created_at, updated_at, likes, viewd, comments, username,
//                    display_name, avatar));
//        }
//
//        Log.i(TAG, "Get " + homeFeeds.size() + " Feeds");
//        return homeFeeds;
//    }

    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }
}
