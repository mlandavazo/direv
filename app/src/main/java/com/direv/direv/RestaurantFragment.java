package com.direv.direv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantFragment extends Fragment {
    private FragmentTabHost mTabHost;

    public RestaurantFragment() {

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
