package com.ps.webskitterassignment.prefrences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ps.webskitterassignment.MyApplication;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "com.ps.webskitterassignment";
    private static AppPreferences instance;
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private AppPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public static AppPreferences getInstance() {
        if (instance instanceof AppPreferences) {
            return instance;
        } else {
            instance = new AppPreferences(MyApplication.applicationContext);
            return instance;
        }
    }

    public void clearAll() {
        prefsEditor.clear().apply();
    }


    public void storeUserToken(String token) {
        prefsEditor.putString("userToken", token);
        prefsEditor.apply();
    }

    public String getUserToken() {
        return appSharedPrefs.getString("userToken", "");
    }

    public void removeUserToken() {
        prefsEditor.remove("userToken");
        prefsEditor.putString("userToken", "");
        prefsEditor.apply();
    }

}
