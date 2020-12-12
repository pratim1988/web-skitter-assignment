package com.ps.webskitterassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.databinding.ActivitySplashBinding;
import com.ps.webskitterassignment.prefrences.AppPreferences;

import androidx.databinding.DataBindingUtil;

public class SplashActivity extends BaseActivity {
    ActivitySplashBinding splashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AppPreferences.getInstance().getUserToken().length()>0){
                    Intent i=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }

                finish();
            }
        }, 2000);
    }
}
