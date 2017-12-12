package com.direv.direv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.direv.direv.DataClasses.Photo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantFragmentRating extends Fragment {

    private RatingBar foodRating;
    private RatingBar serviceRating;
    private RatingBar cleanRating;
    private String mKey;
    List<Double> cleanRatings = new ArrayList<Double>();
    List<Double> foodRatings = new ArrayList<Double>();
    List<Double> serviceRatings = new ArrayList<Double>();
    private static final String TAG = "RestaurantRating";
    private ArrayList<Photo> mPhotos = new ArrayList<Photo>();
    private double mCleanAverage;
    private double mFoodAverage;
    private double mServiceAverage;

    public RestaurantFragmentRating() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurantrating, container, false);

        final Bundle bdl = getArguments();
        foodRating = (RatingBar) view.findViewById(R.id.FoodRating);
        serviceRating = (RatingBar) view.findViewById(R.id.ServiceRating);
        cleanRating = (RatingBar) view.findViewById(R.id.CleanlinessRating);
        mKey = bdl.getString("restaurant_key");

        getAllRatings();

        /*foodRating.setRating(Float.parseFloat(bdl.getString("food_rating")));
        serviceRating.setRating(Float.parseFloat(bdl.getString("service_rating")));
        cleanRating.setRating(Float.parseFloat(bdl.getString("clean_rating")));*/
        return view;
    }

   public void getAllRatings(){
       Log.d(TAG, "getPhotos: getting photos");
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
       Query query = reference
               .child(getString(R.string.dbname_restaurant_photos))
               .child(mKey);
       //.orderByChild("user_id")
       //.equalTo(mRestaurants.get(i).getKey());
       Log.d(TAG, "Testing!");
       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(isAdded()) {
                   for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                       Photo photo = new Photo();
                       Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();


                       photo.setFood_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_food_rating)).toString()));
                       photo.setClean_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_clean_rating)).toString()));
                       photo.setService_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_service_rating)).toString()));

                       Log.d(TAG, "Testing query! " + photo.getService_rating() + photo.getClean_rating()
                       + photo.getFood_rating());

                       serviceRatings.add(photo.getService_rating());
                       foodRatings.add(photo.getFood_rating());
                       cleanRatings.add(photo.getClean_rating());


                   }
                   getCleanRating(cleanRatings);
                   getServiceRating(serviceRatings);
                   getFoodRating(foodRatings);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.d(TAG, "ERRORRRRRR");
           }
       });
   }

    private void getFoodRating(List<Double> foodRatings) {
        double total = 0;
        for(int i = 0; i < foodRatings.size(); i++){
            total += foodRatings.get(i);
        }
        double foodAvg = total / foodRatings.size();
        if((foodAvg % 1) > 0 && (foodAvg % 1) < 0.25){
            mFoodAverage = (foodAvg - (foodAvg % 1));
        }
        else if ((foodAvg % 1) > 0.25 && (foodAvg % 1) < 0.5){
            mFoodAverage = (foodAvg + (0.5 - (foodAvg % 1)));
        }
        else if ((foodAvg % 1) > 0.5){
            mFoodAverage = (foodAvg + (1 - (foodAvg % 1)));
        }
        else{
            mFoodAverage = foodAvg;
        }
        foodRating.setRating(Float.parseFloat(Double.toString(mFoodAverage)));
        Log.d(TAG, "Testing foodAvg! " + mFoodAverage);
    }

    private void getServiceRating(List<Double> serviceRatings) {
        double total = 0;
        for(int i = 0; i < serviceRatings.size(); i++){
            total += serviceRatings.get(i);
        }
        double serviceAvg = total / serviceRatings.size();
        if((serviceAvg % 1) > 0 && (serviceAvg % 1) < 0.25){
            mServiceAverage = (serviceAvg - (serviceAvg % 1));
        }
        else if ((serviceAvg % 1) > 0.25 && (serviceAvg % 1) < 0.5){
            mServiceAverage = (serviceAvg + (0.5 - (serviceAvg % 1)));
        }
        else if ((serviceAvg % 1) > 0.5){
            mServiceAverage = (serviceAvg + (1 - (serviceAvg % 1)));
        }
        else{
            mServiceAverage = serviceAvg;
        }
        serviceRating.setRating(Float.parseFloat(Double.toString(mServiceAverage)));
        Log.d(TAG, "Testing serviceAvg! " + mServiceAverage);
    }

    private void getCleanRating(List<Double> cleanRatings) {
       double total = 0;
       for(int i = 0; i < cleanRatings.size(); i++){
           total += cleanRatings.get(i);
       }
       double cleanAvg = total / cleanRatings.size();
        if((cleanAvg % 1) > 0 && (cleanAvg % 1) < 0.25){
            mCleanAverage = (cleanAvg - (cleanAvg % 1));
        }
        else if ((cleanAvg % 1) > 0.25 && (cleanAvg % 1) < 0.5){
            mCleanAverage = (cleanAvg + (0.5 - (cleanAvg % 1)));
        }
        else if ((cleanAvg % 1) > 0.5){
            mCleanAverage = (cleanAvg + (1 - (cleanAvg % 1)));
        }
        else{
            mCleanAverage = cleanAvg;
        }
        cleanRating.setRating(Float.parseFloat(Double.toString(mCleanAverage)));
        Log.d(TAG, "Testing cleanAvg! " + mCleanAverage);
    }

   /* public double RatingAverage(double[] ratings){
        int reviewsize = reviews.size();
        double avgClean = 0;
        double avgFood = 0;
        double avgService = 0;
        if(type == "clean") {
            for (int i = 0; i < reviewsize; i++) {

                avgClean += reviews.get(i).clean_rating;
            }
            return avgClean/reviewsize;
        }
        else if(type =="food") {
            for (int i = 0; i < reviewsize; i++) {
                avgFood += reviews.get(i).food_rating;
            }
            return avgFood/reviewsize;
        }
        else {
            for (int i = 0; i < reviewsize; i++) {
                avgService += reviews.get(i).service_rating;
            }
            return avgService/reviewsize;
        }

    }*/
}
