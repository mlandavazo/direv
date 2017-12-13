package com.direv.direv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.direv.direv.Utils.AccountSettingsActivity;
import com.direv.direv.Utils.BottomNavigationViewHelper;
import com.direv.direv.Utils.GridImageAdapter;
import com.direv.direv.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by Malachi on 10/16/2017.
 */

public class ProfileActivity extends AppCompatActivity {
//    private Context mContext = ProfileActivity.this;
//    private static final int ACTIVITY_NUM = 3;
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 3;
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Gurjits is activity_profile

//        setupBottomNavigationView();
//        setupActivityWidgets();
//        setProfileImage();
//
//        tempGridSetup();

        init();

    }

    private void init() {
        Log.d(TAG, "init: inflating" + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();
        // Might need to change library for this VVVV
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }

}
