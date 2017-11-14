package com.direv.direv;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.direv.direv.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Malachi on 10/16/2017.
 */

public class FeedActivity extends FragmentActivity {
    private Context mContext = FeedActivity.this;
    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setupBottomNavigationView();
        setupViewPager();
    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragmentNest());
        adapter.addFragment(new FeedFragmentNest());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("FRIENDS");
        tabLayout.getTabAt(1).setText("NEARBY");

    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
// In this case, the fragment displays simple text based on the page

/*
public class FeedActivity extends Fragment {

    private FragmentTabHost mTabHost;

    public FeedActivity() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //View view = inflater.inflate(R.layout.listactivity_feed, container, false);

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.activity_main2);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Friends"),
                FeedFragmentNest.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Nearby"),
                FeedFragmentNest.class, null);

        final ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();

        for (int i = 0; i < 120; i++) {
            feedItems.add(new FeedItem());

        }


        //FeedItemAdapter adapter = new FeedItemAdapter(getActivity(), feedItems);


        //viewRestaurant.setOnClickListener(FeedActivity.this);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
       // ListView listView = (ListView) view.findViewById(R.id.listview);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        //listView.setAdapter(adapter);

        //TextView viewMapText = (TextView) rootView.findViewById(R.id.viewmap_text);
        // viewMapText.setOnClickListener(RoutesFragment.this);

        return mTabHost;
    }

}

*/