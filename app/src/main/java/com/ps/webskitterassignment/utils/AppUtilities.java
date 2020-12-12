package com.ps.webskitterassignment.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.OpenableColumns;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;

import androidx.annotation.RequiresApi;


public class AppUtilities {
    private static AppUtilities comminInstance = null;
    private Context mContext;
    private static Context context = null;
    private static DecimalFormatSymbols symbolsEN_US;
    private static DecimalFormat df;

    /**
     * Recently set context will be returned.
     * If not set it from current class it will
     * be null.
     *
     * @return Context
     */
    public static final Context getContext() {
        return AppUtilities.context;
    }

    /**
     * First set context from every activity
     * before use any static method of AppUtils class.
     *
     * @param ctx
     */
    public static final void setContext(Context ctx) {
        AppUtilities.context = ctx;
    }

    private AppUtilities() {
    }

    public static AppUtilities getInstance(Context mContext) {
        if (comminInstance == null) {
            comminInstance = new AppUtilities();
        }
        comminInstance.mContext = mContext;
        return comminInstance;
    }

    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        /*if(netInfo != null && netInfo.isConnectedOrConnecting()){
            try {
                return isConnected();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
