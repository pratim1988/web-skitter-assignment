package com.ps.webskitterassignment;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static volatile Context applicationContext;
    private static MyApplication mInstance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        MyApplication.applicationContext = this.getApplicationContext();

    }
}
