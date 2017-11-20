package com.direv.direv;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.direv.direv.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantActivity extends AppCompatActivity {
    private Context mContext = RestaurantActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        setupBottomNavigationView();
        setupViewPager();

    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RestaurantFragmentReviews());
        adapter.addFragment(new RestaurantFragmentInfo());
        adapter.addFragment(new RestaurantFragmentRating());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
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
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

    }
}
/*
public class RestaurantActivity extends Fragment {
    private FragmentTabHost mTabHost;

    public RestaurantActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.listactivity_feed, container, false);

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.activity_main2);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Reviews"),
                RestaurantFragmentReviews.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Info"),
                RestaurantFragmentInfo.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Rating"),
                RestaurantFragmentRating.class, null);

        final ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();

        for (int i = 0; i < 120; i++) {
            feedItems.add(new FeedItem());
        }

        FeedItemAdapter adapter = new FeedItemAdapter(getActivity(), feedItems);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        //ListView listView = (ListView) view.findViewById(R.id.listview);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        //listView.setAdapter(adapter);

        //TextView viewMapText = (TextView) rootView.findViewById(R.id.viewmap_text);
        // viewMapText.setOnClickListener(RoutesFragment.this);

        return mTabHost;
    }
}
*/