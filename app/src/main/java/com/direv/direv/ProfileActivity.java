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

//    private void tempGridSetup() {
//        ArrayList<String> imgURLs = new ArrayList<>();
//        imgURLs.add("https://i.pinimg.com/originals/6d/8e/67/6d8e675659aae9018119a26929acc773.jpg");
//        imgURLs.add("http://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2017/04/lasana.jpg");
//        imgURLs.add("http://farm9.staticflickr.com/8229/8435747903_b228c03d05_c.jpg");
//        imgURLs.add("https://www.bbcgoodfood.com/sites/default/files/styles/category_retina/public/recipe-collections/collection-image/2013/05/mexican-chicken-burger_1.jpg?itok=LJYhlfBT");
//        imgURLs.add("http://www.bandt.com.au/information/uploads/2014/10/iStock_000000923255_Small-1260x840.jpg");
//        imgURLs.add("http://konjo.com.au/wp-content/uploads/2015/07/AS-FBgoodfood-aw-20150213123813301649-620x0.jpg");
//        imgURLs.add("http://www.zomppa.com/wp-content/uploads/2011/12/pie.jpg");
//        imgURLs.add("http://nebula.wsimg.com/82c2438bcec6ce077fdb9f5777483347?AccessKeyId=FB771320EF82836BDCA7&disposition=0&alloworigin=1");
//        imgURLs.add("http://yummyyummyaz.com/images/banner03.jpg");
////        imgURLs.add("https://res.cloudinary.com/simpleview/image/upload/c_fill,f_auto,h_440,q_80,w_945/v1/clients/rockford/header_restaurant_asian_shogun_589914cb-632b-4969-8204-0bd095a5722b.jpg");
//
//        setupImageGrid(imgURLs);
//    }
//
//    private void setupImageGrid(ArrayList<String> imgURLS) {
//        GridView gridView = (GridView) findViewById(R.id.gridView);
//
//        int gridWidth = getResources().getDisplayMetrics().widthPixels;
//        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//
//        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLS);
//        gridView.setAdapter(adapter);
//    }
//
//    private void setProfileImage() {
//        Log.d(TAG, "setProfileImage: setting profile photo");
//        String img = "https://i.pinimg.com/originals/6d/8e/67/6d8e675659aae9018119a26929acc773.jpg";
//        UniversalImageLoader.setImage(img, profilePhoto, mProgressBar, "");
//    }
//
//    private void setupActivityWidgets() {
//        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
//        mProgressBar.setVisibility(View.GONE);
//        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
//    }
//
//    private void setupBottomNavigationView(){
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }
//
//}
//// In this case, the fragment displays simple text based on the page
//    /*
//public class ProfileActivity extends Fragment {
//
//    public ProfileActivity() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_profile, container, false);
//        return view;
//    }
}
