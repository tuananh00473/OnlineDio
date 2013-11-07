package com.qsoft.ondio.lazzyload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.RelativeLayout;
import com.qsoft.ondio.R;
import com.qsoft.ondio.activity.ProfileFragment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: anhnt
 * Date: 11/7/13
 * Time: 9:08 AM
 */
public class ProfileCoverImageLoader
{
    final int stub_id = R.drawable.profile_cover_image;

    MemoryCache memoryCache;
    FileCache fileCache;
    private Map<RelativeLayout, String> relativeLayoutMaps;
    ExecutorService executorService;
    Handler handler;//handler to display images in UI thread
    private ProfileFragment profileFragment;

    public ProfileCoverImageLoader(Context context, ProfileFragment profileFragment)
    {
        this.profileFragment = profileFragment;
        memoryCache = new MemoryCache();
        fileCache = new FileCache(context, "profileCoverImage");
        relativeLayoutMaps = Collections.synchronizedMap(new WeakHashMap<RelativeLayout, String>());
        executorService = Executors.newFixedThreadPool(5);
        handler = new Handler();
    }

    public void DisplayImage(String url, RelativeLayout rlCoverImage)
    {
        relativeLayoutMaps.put(rlCoverImage, url);
        Bitmap bitmap = memoryCache.get(url);
        if (null == bitmap)
        {
            queuePhoto(url, rlCoverImage);
            bitmap = BitmapFactory.decodeResource(profileFragment.getResources(), stub_id);
        }
        Drawable cover = new BitmapDrawable(bitmap);
        rlCoverImage.setBackgroundDrawable(cover);
    }

    private void queuePhoto(String url, RelativeLayout rlCoverImage)
    {
        PhotoToLoad p = new PhotoToLoad(url, rlCoverImage);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url)
    {
        File f = fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
        {
            return b;
        }

        //from web
        try
        {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
            {
                memoryCache.clear();
            }
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f)
    {
        try
        {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true)
            {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public RelativeLayout rlCoverImage;

        public PhotoToLoad(String url, RelativeLayout rlCoverImage)
        {
            this.url = url;
            this.rlCoverImage = rlCoverImage;
        }
    }

    class PhotosLoader implements Runnable
    {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad)
        {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run()
        {
            try
            {
                if (imageReused(photoToLoad))
                {
                    return;
                }
                Bitmap bmp = getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);
                if (imageReused(photoToLoad))
                {
                    return;
                }
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            }
            catch (Throwable th)
            {
                th.printStackTrace();
            }
        }
    }

    boolean imageReused(PhotoToLoad photoToLoad)
    {
        String tag = relativeLayoutMaps.get(photoToLoad.rlCoverImage);
        if (tag == null || !tag.equals(photoToLoad.url))
        {
            return true;
        }
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p)
        {
            bitmap = b;
            photoToLoad = p;
        }

        public void run()
        {
            if (imageReused(photoToLoad))
            {
                return;
            }
            if (null == bitmap)
            {
                bitmap = BitmapFactory.decodeResource(profileFragment.getResources(), stub_id);
            }
            Drawable cover = new BitmapDrawable(bitmap);
            photoToLoad.rlCoverImage.setBackgroundDrawable(cover);
        }
    }

    public void clearCache()
    {
        memoryCache.clear();
        fileCache.clear();
    }
}
