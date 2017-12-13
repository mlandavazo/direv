package com.direv.direv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.direv.direv.DataClasses.Comment;
import com.direv.direv.DataClasses.Like;
import com.direv.direv.DataClasses.Location;
import com.direv.direv.DataClasses.Photo;
import com.direv.direv.DataClasses.Restaurant;
import com.direv.direv.Utils.MainfeedListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Malachi on 11/18/2017.
 */

public class FeedNearbyFragment extends Fragment {
    private static final String TAG = "FeedNearbyFragment";

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<Restaurant> mRestaurants;
    private ListView mListView;
    private MainfeedListAdapter mAdapter;
    private int mResults;
    private double mLongitude = 0.0;
    private double mLatitude = 0.0;
    private ArrayList<String> mKeys;
    private int mLastPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearbyfeed, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        mRestaurants = new ArrayList<>();
        mPhotos = new ArrayList<>();
       // mLatitude = Double.parseDouble(getArguments().getString("latitude"));
       // mLongitude = Double.parseDouble(getArguments().getString("longitude"));

        if (isAdded()) {
            getRestaurants();

        }
        else{
            Log.d(TAG, "NearbyFragment: fragment is not attached to activity");
        }
        return view;
    }

    private void getRestaurants(){
        Log.d(TAG, "getRestaurants: searching for nearby restaurants");
        mLatitude = ((FeedActivity)getActivity()).getmLatitude();
        mLongitude = ((FeedActivity)getActivity()).getmLongitude();
        Log.d(TAG, "getRestaurants: my location is: " + mLatitude + " " + mLongitude);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(isAdded()) {
                    DataSnapshot dataSnapshot1 = dataSnapshot.child("restaurants");
                    Iterable<DataSnapshot> restaurantSnapshot = dataSnapshot1.getChildren();

                    for (DataSnapshot ds : restaurantSnapshot) {
                        //String key = (String) ds.getKey();
                        Restaurant restaurant = ds.getValue(Restaurant.class);

                        // mKeys.add(restaurant.getKey());
                        final ArrayList<Photo> reviews = new ArrayList<Photo>();


                        Log.d(TAG, "onDataChange: Restaurant ID being looked at: " + restaurant.getKey());


                        //Log.d(TAG, "onDataChange: Coords are: " + mLatitude + " " + mLongitude);
                        double check = checkDistance(mLatitude, mLongitude, restaurant);
                        //Log.d(TAG, "onDataChange: Distance is: " + check);
                        if (check <= 10000) {
                            Log.d(TAG, "onDataChange: Found a nearby restaurant " +
                                    checkDistance(mLatitude, mLongitude, restaurant) + " meters away!");
                            if (ds.getKey() != null) {
                                //mKeys.add(ds.getKey());
                            }
                            //restaurant.setReviews(reviews);
                            boolean found = false;
                            for (int i = 0; i < mRestaurants.size(); i++) {
                                if (mRestaurants.get(i).getLocation().getId().equals(restaurant.getLocation().getId())) {
                                    found = true;
                                    mRestaurants.remove(mRestaurants.get(i));
                                    mRestaurants.add(restaurant);
                                    Log.d(TAG, "onDataChange: Already in the list! ");
                                }
                            }
                            if (!found) {
                                mRestaurants.add(restaurant);
                            }
                        }

                    }
                    //get the photos
                    getPhotos();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });
        //getPhotos();

    }

    private double checkDistance(double mLatitude, double mLongitude, Restaurant restaurant) {
        Location location = restaurant.getLocation();
        double lat2 = Double.parseDouble(location.getLat());
        double lon2 = Double.parseDouble(location.getLng());

        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-mLatitude);  // deg2rad below
        double dLon = deg2rad(lon2-mLongitude);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(mLatitude)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return (d * 1000);

    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    private void getPhotos(){
        Log.d(TAG, "getPhotos: getting photos");
        for(int i = 0; i < mRestaurants.size(); i++) {
            final int count = i;
            // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference
                    .child(getString(R.string.dbname_restaurant_photos))
                    .child(mRestaurants.get(i).getKey());
                    //.orderByChild("user_id")
                    //.equalTo(mRestaurants.get(i).getKey());
            Log.d(TAG, "Testing!");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "Testing query!");
                        Photo photo = new Photo();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                        photo.setCaption(objectMap.get(getString(R.string.field_caption)).toString());
                        photo.setPhoto_id(objectMap.get(getString(R.string.field_photo_id)).toString());
                        photo.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
                        photo.setDate_created(objectMap.get(getString(R.string.field_date_created)).toString());
                        photo.setImage_path(objectMap.get(getString(R.string.field_image_path)).toString());
                        photo.setFood_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_food_rating)).toString()));
                        photo.setClean_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_clean_rating)).toString()));
                        photo.setService_rating(Double.parseDouble(objectMap.get(getString(R.string.field_image_service_rating)).toString()));
                        photo.setRestaurant_name(objectMap.get(getString(R.string.field_rest_name)).toString());
                        photo.setRestaurant_key(objectMap.get(getString(R.string.field_rest_key)).toString());

                        Log.d(TAG, "CHECKING QUERY: Photo info -  " + photo.getRestaurant_key());
                        ArrayList<Like> likes = new ArrayList<Like>();
                        for (DataSnapshot dSnapshot : singleSnapshot
                                .child(getString(R.string.field_likes)).getChildren()) {
                            Like like = new Like();
                            like.setUser_id(dSnapshot.getValue(Like.class).getUser_id());

                            likes.add(like);
                        }
                        ArrayList<Comment> comments = new ArrayList<Comment>();
                        for (DataSnapshot dSnapshot : singleSnapshot
                                .child(getString(R.string.field_comments)).getChildren()) {
                            Comment comment = new Comment();
                            comment.setUser_id(dSnapshot.getValue(Comment.class).getUser_id());
                            comment.setComment(dSnapshot.getValue(Comment.class).getComment());
                            comment.setDate_created(dSnapshot.getValue(Comment.class).getDate_created());
                            comments.add(comment);
                        }

                        photo.setLikes(likes);
                        photo.setComments(comments);

                        boolean found = false;
                        for(int i = 0; i < mPhotos.size(); i++){
                            if (mPhotos.get(i).getPhoto_id().equals(photo.getPhoto_id())){
                                found = true;
                                mPhotos.remove(mPhotos.get(i));
                                mPhotos.add(photo);
                                Log.d(TAG, "onDataChange: Photo already in the list! ");
                            }
                        }
                        if (!found) {
                            mPhotos.add(photo);
                        }
                    }

                    if(count >= mRestaurants.size() - 1){
                        Log.d(TAG, "getPhotos: number of photos found: " + mPhotos.size());
                        //display our photos
                        displayPhotos();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "ERRORRRRRR");
                }
            });

        }
        /*for(int i = 0; i < mRestaurants.size(); i++){
            final int count = i;
            List<Photo> reviews = mRestaurants.get(i).getReviews();
            for (int j = 0; j < reviews.size(); j++){
                mPhotos.add(reviews.get(j));
            }
            */


    }

    private void displayPhotos(){
        mPaginatedPhotos = new ArrayList<>();
        //mPaginatedPhotos.clear();
        Log.d(TAG, "displayPhotos: displaying photos!!!!!!!!");
        if(mPhotos != null){
            try{
                Collections.sort(mPhotos, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o2.getDate_created().compareTo(o1.getDate_created());
                    }
                });

                int iterations = mPhotos.size();

                if(iterations > 10){
                    iterations = 10;
                }

                mResults = 10;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
                }

                if(mListView.getAdapter() == null) {
                    mAdapter = new MainfeedListAdapter(getActivity(), R.layout.list_item, mPaginatedPhotos);
                    mListView.setAdapter(mAdapter);
                }
                else{
                    //mAdapter.updateData(myNewData);  //update adapter's data
                    mAdapter.notifyDataSetChanged();//notifies any View reflecting data to refresh
                    //mListView.setAdapter(mAdapter);
                    //mListView.invalidateViews();
                }

                //mListView.setSelection(mAdapter.getListPosition());
                //mListView.setAdapter(mAdapter);

               /* mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView parent, View v, int position, long id){
                        Log.d(TAG, "NearbyFragment: item clicked at position: " + position);
                        //mListView.setSelection(position);
                        mLastPosition = position;

                    }
                });*/




            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }
    }


    public void displayMorePhotos(){
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try{

            if(mPhotos.size() > mResults && mPhotos.size() > 0){

                int iterations;
                if(mPhotos.size() > (mResults + 10)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mPhotos.size() - mResults;
                }

                //add the new photos to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
                }
                mResults = mResults + iterations;
                mAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
        }catch (IndexOutOfBoundsException e){
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
        }
    }


   /* public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
            mLongitude = extras.getDouble("Longitude");
            mLatitude = extras.getDouble("Latitude");
        }
    }*/

}
