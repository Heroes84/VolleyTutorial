package com.volleytutorial.app;

import android.app.Application;

/**
 * Created by R4D3K on 2014-06-16.
 */
public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
