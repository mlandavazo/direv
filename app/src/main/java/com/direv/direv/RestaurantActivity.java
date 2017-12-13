package com.direv.direv;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.direv.direv.DataClasses.Photo;
import com.direv.direv.Utils.BottomNavigationViewHelper;
import com.direv.direv.Utils.RestaurantFeedAdapter;
import com.direv.direv.Utils.ViewCommentsFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantActivity extends AppCompatActivity implements
    RestaurantFeedAdapter.OnLoadMoreItemsListener{
        @Override
        public void onLoadMoreItems() {
            if (getSupportFragmentManager().equals(RestaurantFragmentReviews.class)) {
                Log.d(TAG, "onLoadMoreItems: displaying more photos");
                RestaurantFragmentReviews fragment = (RestaurantFragmentReviews) getSupportFragmentManager()
                        .findFragmentByTag("android:switcher:" + R.id.viewpager_container + ":" + mViewPager.getCurrentItem());
                if (fragment != null) {
                    fragment.displayMorePhotos();
                }
            }
        }
    private static final String TAG = "RestaurantActivity";
    private Context mContext = RestaurantActivity.this;
    private static final int ACTIVITY_NUM = 1;
    private String mrestname;
    private String mfoodrating;
    private String mcleanrating;
    private String mservicerating;
    private TextView mRestName;
    private String mrestaurantkey;

    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mViewPager=(ViewPager)findViewById(R.id.viewpager_container);
        mFrameLayout=(FrameLayout)findViewById(R.id.container);
        mRelativeLayout=(RelativeLayout)findViewById(R.id.relLayoutParent);

        mrestname = getIntent().getStringExtra("restaurant_name");
        mfoodrating = getIntent().getStringExtra("food_rating");
        mcleanrating = getIntent().getStringExtra("clean_rating");
        mservicerating = getIntent().getStringExtra("service_rating");
        mrestaurantkey = getIntent().getStringExtra("restaurant_key");

        mRestName = (TextView) findViewById(R.id.rest_name);
        mRestName.setText(mrestname);

        setupBottomNavigationView();
        setupViewPager();

    }
    public void onCommentThreadSelected(Photo photo, String callingActivity){
        Log.d(TAG, "onCommentThreadSelected: selected a coemment thread for restaurant "
                + photo.getRestaurant_key());

        ViewCommentsFragment fragment  = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), photo);
        args.putString(getString(R.string.restaurant_activity), getString(R.string.restaurant_activity));
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();

    }

    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }


    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFrameLayout.getVisibility() == View.VISIBLE){
            showLayout();
        }
    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final RestaurantFragmentRating restaurantFragmentRating = new RestaurantFragmentRating();
        final RestaurantFragmentReviews restaurantFragmentReviews = new RestaurantFragmentReviews();

        final Bundle bdl = new Bundle();
        bdl.putString("clean_rating",mcleanrating );
        bdl.putString("food_rating",mfoodrating );
        bdl.putString("service_rating",mservicerating );
        bdl.putString("restaurant_key", mrestaurantkey);
        restaurantFragmentRating.setArguments(bdl);

        final Bundle bdl2 = new Bundle();
        bdl2.putString("restaurant_key",mrestaurantkey);
        restaurantFragmentReviews.setArguments(bdl2);

        adapter.addFragment(restaurantFragmentReviews);
        adapter.addFragment(new RestaurantFragmentInfo());
        adapter.addFragment(restaurantFragmentRating);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("REVIEWS");
        tabLayout.getTabAt(1).setText("INFO");
        tabLayout.getTabAt(2).setText("RATING");


    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
