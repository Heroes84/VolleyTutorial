package com.volleytutorial.app.request;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.volleytutorial.app.App;

/**
 * Created by R4D3K on 2014-06-01.
 */
public class VolleySingleton {

    private static final VolleySingleton sInstance = new VolleySingleton();
    private final RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;

    private VolleySingleton() {
        this.mRequestQueue = Volley.newRequestQueue(App.getInstance().getApplicationContext());
        this.mImageLoader = new ImageLoader(mRequestQueue, new ImageCache());
    }

    public static VolleySingleton getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
