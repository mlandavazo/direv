package com.direv.direv;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Malachi on 10/16/2017.
 */


public class MainTabsAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3", "Tab4", "Tab5" };
    private Context context;

    public MainTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new FeedFragment();
        } else if (position == 2) {
            return new CameraFragment();
        } else if (position == 3) {
            return new ProfileFragment();
        } else {
            return new SettingsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return null;
    }
}
