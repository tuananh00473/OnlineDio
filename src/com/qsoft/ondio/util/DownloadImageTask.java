package com.qsoft.ondio.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * User: AnhNT
 * Date: 11/4/13
 * Time: 10:01 AM
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    private static final String TAG = "DownloadImageTask";
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls)
    {
        String url_display = urls[0];
        Bitmap mIcon11 = null;
        try
        {
            Log.d(TAG, "loading image.");
            InputStream in = new java.net.URL(url_display).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result)
    {
        if (result != null)
        {

            bmImage.setImageBitmap(result);
        }
    }
}
