package com.segeval.safedrive.model;

import android.app.Application;
import android.content.Context;


public class SafeDriveApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SafeDriveApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return SafeDriveApplication.context;
    }
}
