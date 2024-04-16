package com.example.android_coins;

import android.app.Application;
import android.content.Context;

public class CoinApplication extends Application {
    private static Context APPLICATION_CONTEXT;

    public static Context getContext() {
        return APPLICATION_CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION_CONTEXT = this.getApplicationContext();
    }
}
