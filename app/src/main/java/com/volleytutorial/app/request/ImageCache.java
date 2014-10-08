package com.volleytutorial.app.request;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by R4D3K on 2014-06-16.
 */
public class ImageCache implements ImageLoader.ImageCache {

    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }


}
