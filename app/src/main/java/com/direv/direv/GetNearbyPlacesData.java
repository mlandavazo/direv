package com.direv.direv;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.direv.direv.Utils.FirebaseMethods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    private static final String TAG = "GetNearbyPlaces";
    private String googlePlacesData;
    private GoogleMap mMap;
    private Context mContext;
    private GoogleMap mMapDets;
    private List<String> placeIDs = new ArrayList<String>();
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
        mMapDets = (GoogleMap)objects[0];
        url = (String)objects[1];
        mContext = (Context)objects[2];

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
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
       // Log.d("nearbyplacesdata","called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        mFirebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG,"Getting nearby places data!");
        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String address = googlePlace.get("vicinity");
            String id = googlePlace.get("id");
            String placeID = googlePlace.get("place_id");
            try {
                placeIDs.add(placeID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
           // long phone = Long.parseLong(googlePlace.get("international_phone_number"));
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String lati = googlePlace.get("lat");
            String lngi = googlePlace.get("lng");
            Log.d(TAG,"Place: " + placeIDs.get(i) + " Name: " + placeName + " Address: " + address + " ID: " + id + " Place ID: " + placeID);
            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            checkIfLocationExists(id, placeName, address, placeID, lati, lngi);
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
        /*
        Object dataTransfer[] = new Object[2];

        for(int i = 0; i < placeIDs.size(); i++){
            dataTransfer[0] = mMapDets;
            dataTransfer[1] = getPlaceDetailsUrl(placeIDs.get(i));
            GetPlaceDetails getPlaceDetails = new GetPlaceDetails();
            getPlaceDetails.execute(dataTransfer);
        }
        */
    }

    public List<String> getPlaceIDs(){
        Log.d(TAG,"Place size: " + placeIDs.size());
        return placeIDs;
    }
    private String getPlaceDetailsUrl(String placeID) // Use GooglePlaces API functions to find URL
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        googlePlaceUrl.append(placeID);
        googlePlaceUrl.append("&key="+"AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo");

        Log.d("getPlaceDetailsURl", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }
  /*  private void getPlaceDetails(String placeID) {
        String newurl = getPlaceDetailsUrl(placeID);
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = newurl;
        GetPlaceDetails getPlaceDetails = new GetPlaceDetails();
        getPlaceDetails.execute(mMap,newurl);
    }*/

    private void checkIfLocationExists(final String id, final String name, final String address, final String placeID, final String lat, final String lon) {
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
                    //mFirebaseMethods.addNewLocationAndRestaurant(id, name, address, placeID, lat, lon);

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
