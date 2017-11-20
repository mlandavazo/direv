package com.direv.direv;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class MainActivity extends FragmentActivity {
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("",getResources().getDrawable(R.drawable.homeicon)),
                HomeActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("",getResources().getDrawable(R.drawable.feedicon)),
                FeedActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("",getResources().getDrawable(R.drawable.cameraicon)),
                CameraActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("",getResources().getDrawable(R.drawable.usernameicon)),
                ProfileActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator("",getResources().getDrawable(R.drawable.settingsicon)),
                SettingsActivity.class, null);
    }

}
