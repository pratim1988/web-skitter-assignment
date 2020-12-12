package com.ps.webskitterassignment.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.databinding.ActivityMainBinding;
import com.ps.webskitterassignment.fragment.LocationFragment;
import com.ps.webskitterassignment.fragment.ProfileFragment;
import com.ps.webskitterassignment.fragment.UserListFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;
    LocationFragment locationFragment = null;
    ProfileFragment profileFragment = null;
    UserListFragment userListFragment = null;
    public final static String LOCATION = "Location.skitter";
    public final static String PROFILE = "Profile.skitter";
    public final static String USER_LIST = "User_List.skitter";
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mainBinding.navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_location:
                                loadLocationFragment();
                                break;
                            case R.id.navigation_profile:
                                loadProfileFragment();
                                break;
                            case R.id.navigation_user_list:
                                loadUserListFragment();
                                break;
                        }
                        return false;
                    }
                });

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            loadLocationFragment();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void loadLocationFragment() {
        if (locationFragment == null)
            locationFragment = new LocationFragment();
        mFragment = locationFragment;
        loadFragment(LOCATION, false, mFragment);
    }

    private void loadProfileFragment() {
        if (profileFragment == null)
            profileFragment = new ProfileFragment();
        mFragment = profileFragment;
        loadFragment(PROFILE, false, mFragment);
    }

    private void loadUserListFragment() {
        if (userListFragment == null)
            userListFragment = new UserListFragment();
        mFragment = userListFragment;
        loadFragment(USER_LIST, false, mFragment);
    }

    public void loadFragment(String tag, boolean addToStack, Fragment setFragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragment = setFragment;
        if (addToStack) {
            mFragmentTransaction.add(R.id.container, mFragment, tag);
            mFragmentTransaction.addToBackStack(tag).commit();
        } else {
            mFragmentTransaction.replace(R.id.container, mFragment, tag);
            mFragmentTransaction.commitAllowingStateLoss();
        }
    }
}