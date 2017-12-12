package com.direv.direv;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.direv.direv.Utils.FirebaseMethods;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Malachi on 12/2/2017.
 */

class GetPlaceDetails extends AsyncTask<Object, String, String> {
    private static final String TAG = "GetPlaceDetails";
    private String googlePlacesData;
    private Context mContext;
    private String name;
    private String address;
    private String id;
    private String placeID;
    private String lat;
    private String lng;
    private GoogleMap mMap;
    String url;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    @Override
    protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        mContext = (Context)objects[2];
        name = (String)objects[3];
        address = (String)objects[4];
        id = (String)objects[5];
        placeID = (String)objects[6];
        lat = (String)objects[7];
        lng = (String)objects[8];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        List<HashMap<String, String>> nearbyPlaceList;
        DetailsParser parser = new DetailsParser();
        nearbyPlaceList = parser.parse(s);
        // Log.d("nearbyplacesdata","called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        mFirebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG,"Getting place details!");
        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String phone = googlePlace.get("formatted_phone_number");

            Log.d(TAG,"Phone: " + phone);

            checkIfLocationExists(id, name, address, placeID, lat, lng, phone);
            // getPlaceDetails(placeID);
           /* LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));*/
        }

    }

    private void checkIfLocationExists(final String id, final String name, final String address,
                                       final String placeID, final String lat, final String lon, final String phone) {
        Log.d(TAG, "checkIfLocationExists: Checking if  " + id + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("locations")
                .orderByChild("id")
                .equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    //add the location
                    mFirebaseMethods.addNewRestaurant(id, name, address, placeID, lat, lon, phone);

                }
                else if (dataSnapshot.exists()){
                    Log.d(TAG, "location is already in database");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
