package com.direv.direv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantFragmentInfo extends Fragment {


    public RestaurantFragmentInfo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurantinfo, container, false);

        return view;
    }
}
