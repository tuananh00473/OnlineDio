package com.qsoft.ondio.accountmanager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the comminication with Parse.com
 * <p/>
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class ParseComServer implements ServerAuthenticate
{

    private final static String APP_ID = "iRnc8I1X0du5q6HrJtZW0a5DlB0JcpOQbjA6chha";
    private final static String REST_API_KEY = "tv1xCdYKTwI3p205KHCn1yWpbVj2OHldV9cPZuNZ";

    /**
     * Return the basic headers to connect our app's parse.com details
     *
     * @return
     */
    public static List<Header> getAppParseComHeaders()
    {
        List<Header> ret = new ArrayList<Header>();
        ret.add(new BasicHeader("X-Parse-Application-Id", APP_ID));
        ret.add(new BasicHeader("X-Parse-REST-API-Key", REST_API_KEY));
        return ret;
    }

    @Override
    public User userSignUp(String name, String email, String pass, String authType) throws Exception
    {

        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();

        URL realUrl = null;
        try
        {
            realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", "trangnh299@gmail.com");
            jsonObject.addProperty("password", "e10adc3949ba59abbe56e057f20f883e");
            jsonObject.addProperty("grant_type", "password");
            jsonObject.addProperty("client_id", "123456789");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String respond = EntityUtils.toString(httpResponse.getEntity());

            User createdUser = new Gson().fromJson(respond, User.class);

            return createdUser;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User userSignIn(String user, String pass, String authType) throws Exception
    {

        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();

        URL realUrl = null;
        try
        {
            realUrl = new URL(url);
            HttpPost httpPost = new HttpPost(realUrl.toURI());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", "trangnh299@gmail.com");
            jsonObject.addProperty("password", "e10adc3949ba59abbe56e057f20f883e");
            jsonObject.addProperty("grant_type", "password");
            jsonObject.addProperty("client_id", "123456789");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject respondJson = new JSONObject(responseString);
            String token = respondJson.getString("access_token");
            User loggedUser = new Gson().fromJson(responseString, User.class);
            return loggedUser;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }
}
