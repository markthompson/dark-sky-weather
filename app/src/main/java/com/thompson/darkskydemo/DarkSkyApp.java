package com.thompson.darkskydemo;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mark Thompson Jr.
 */

public class DarkSkyApp extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;//getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
