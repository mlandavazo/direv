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
import com.direv.direv.DataClasses.Photo;
import com.direv.direv.Utils.RestaurantFeedAdapter;
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
 * Created by Malachi on 11/3/2017.
 */

public class RestaurantFragmentReviews extends Fragment {
    private static final String TAG = "FeedNearbyFragment";

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ListView mListView;
    private RestaurantFeedAdapter mAdapter;
    private int mResults;
    private String mKey;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurantpage_feed, container, false);

        final Bundle bdl = getArguments();
        mListView = (ListView) view.findViewById(R.id.listView);
        mPhotos = new ArrayList<>();
        mKey = bdl.getString("restaurant_key");
        // mLatitude = Double.parseDouble(getArguments().getString("latitude"));
        // mLongitude = Double.parseDouble(getArguments().getString("longitude"));
        if(isAdded()) {
            getPhotos();
        }
        else{
            Log.d(TAG, "RestFragment: fragment is not attached to activity");
        }

        return view;
    }

    private void getPhotos(){
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
                        for (int i = 0; i < mPhotos.size(); i++) {
                            if (mPhotos.get(i).getPhoto_id().equals(photo.getPhoto_id())) {
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

                    displayPhotos();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "ERRORRRRRR");
            }
        });

    }

    private void displayPhotos(){
        mPaginatedPhotos = new ArrayList<>();
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

                mAdapter = new RestaurantFeedAdapter(getActivity(), R.layout.list_item, mPaginatedPhotos);
                mListView.setAdapter(mAdapter);

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