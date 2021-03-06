package com.direv.direv;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.direv.direv.Utils.BottomNavigationViewHelper;
import com.direv.direv.Utils.FirebaseMethods;
import com.direv.direv.Utils.UniversalImageLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Malachi on 10/16/2017.
 * Edited by Gurjit 10/20 - added google Maps functionality
 * Edited by Gurjit 11/01 - added google places functionality

 */


// In this case, the fragment displays simple text based on the page
public class HomeActivity extends FragmentActivity implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView mMikes;
    private TextView mSonic;
    private TextView mPanda;
private FeedNearbyFragment nearbyFrag;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    public Location HomeLocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private static final String TAG = "HomeActivity";
    Object dTransfer[] = new Object[2];
    String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG,"OnCreate : starting");
        mContext = HomeActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        setupFirebaseAuth();
        initImageLoader();
        setupBottomNavigationView();
        mMikes= (TextView) findViewById(R.id.mmikes);
        mPanda= (TextView) findViewById(R.id.panda);
        mSonic= (TextView) findViewById(R.id.sonic);
        nearbyFrag = new FeedNearbyFragment();
        nearbyFrag.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    /*
      Manipulates the map once available.
      This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
  /*  public void getLoc(){
        GetNearbyPlacesData gb = new GetNearbyPlacesData();
        dTransfer[0] = mMap;
        dTransfer[1] = link;
        DataParser dp = new DataParser();

    }*/

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        //Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    public void onClick(View v)
    {
        mMikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need to do fragment transaction here
                //ViewCommentsFragment fragment  = new ViewCommentsFragment();
                Intent intent = new Intent(mContext, RestaurantActivity.class);
                Bundle args = new Bundle();
                intent.putExtra("restaurant_name","Mountain Mike's");
                intent.putExtra("clean_rating", "3");
                intent.putExtra("service_rating", "4");
                intent.putExtra("food_rating", "5");
                intent.putExtra("restaurant_key", "-L02UGz2ekkOeZipD2FR");

                mContext.startActivity(intent, args);
            }
        });
        mSonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need to do fragment transaction here
                //ViewCommentsFragment fragment  = new ViewCommentsFragment();
                Intent intent = new Intent(mContext, RestaurantActivity.class);
                Bundle args = new Bundle();
                intent.putExtra("restaurant_name","Sonic Drive-In");
                intent.putExtra("clean_rating", "3");
                intent.putExtra("service_rating", "3");
                intent.putExtra("food_rating", "3");
                intent.putExtra("restaurant_key", "-L01IYA7YeZ8MBmc-pdX");

                mContext.startActivity(intent, args);
            }
        });
        mPanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need to do fragment transaction here
                //ViewCommentsFragment fragment  = new ViewCommentsFragment();
                Intent intent = new Intent(mContext, RestaurantActivity.class);
                Bundle args = new Bundle();
                intent.putExtra("restaurant_name","Panda Express");
                intent.putExtra("clean_rating", "2");
                intent.putExtra("service_rating", "3");
                intent.putExtra("food_rating", "3");
                intent.putExtra("restaurant_key", "-L02UGz8mzOfwXaiq14u");

                mContext.startActivity(intent, args);
            }
        });
        Object dataTransfer[] = new Object[3];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();  //needed for buttons
        GetNearbyPlacesData getNearbyPlacesData2 = new GetNearbyPlacesData(); // needed for specific search
        switch(v.getId()) //This switch method checks which button is being pressed and then calls
        // the appropriate methods for the action
        {
            case R.id.B_search:    //if we are going to search, take the string into the address list and
                // find it with our  dataParse/getNearbyPlaces
                EditText tf_location =  findViewById(R.id.TF_location);
                String location = tf_location.getText().toString();
                List<Address> addressList;


                if(!location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 5);

                        if(addressList != null)
                        {
                            for(int i = 0;i<addressList.size();i++)
                            {
                                LatLng latLng = new LatLng(addressList.get(i).getLatitude() , addressList.get(i).getLongitude());
                                latitude = addressList.get(i).getLatitude();
                                longitude = addressList.get(i).getLongitude();
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(location);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.B_search2:    //search for specific places
                mMap.clear();
                EditText tf_location2 =  findViewById(R.id.TF_location2);
                String location2 = tf_location2.getText().toString();
                String url2 = getUrl2(latitude, longitude, location2);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url2;
                dataTransfer[2]=mContext;

                getNearbyPlacesData2.execute(dataTransfer); // we use nearbyplacesdata2 because its a different URL search
                getNearbyPlacesData.execute(dataTransfer);


                break;
            case R.id.B_restaurants: //send search  of restaurants into get URL

                mMap.clear();
                String restaurant = "restaurant";
                String url = getUrl(latitude, longitude, restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = mContext;


                getNearbyPlacesData.execute(dataTransfer);
                //Toast.makeText(HomeFragment.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.B_bar: //send search  of bars into get URL

                mMap.clear();
                String bar = "bar";
                url = getUrl(latitude, longitude, bar);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = mContext;

                getNearbyPlacesData.execute(dataTransfer);
                //Toast.makeText(HomeFragment.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.B_cafe: //find nearby cafe's using get URL and transferring data to map
                // we then pass the data of the map to getNearbyPlacesData
                mMap.clear();
                String cafe = "cafe";
                url = getUrl(latitude, longitude, cafe);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = mContext;

                getNearbyPlacesData.execute(dataTransfer);

                break;


            case R.id.B_to:
                Toast.makeText(HomeActivity.this, "Showing our location", Toast.LENGTH_SHORT).show();
                latitude=lastlocation.getLatitude();
                longitude = lastlocation.getLongitude();
        }

    }


    private void getLocations() {

    }


    private String getUrl(double latitude , double longitude , String nearbyPlace) // Use GooglePlaces API functions to find URL
    {
        //append the URL appropriately
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString(); //return URL
    }
    private String getUrl2(double latitude , double longitude , String nearbyPlace) // Use GooglePlaces API functions to find URL
    {

        if(nearbyPlace.contains(" ")){

            int index =0;
            char[] myPlace= nearbyPlace.toCharArray();
            index = nearbyPlace.indexOf(" ");
            myPlace[index] = '_';
            nearbyPlace = String.valueOf(myPlace);
        }
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlaceUrl.append("query="+nearbyPlace);
        googlePlaceUrl.append("&location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+"100");
        googlePlaceUrl.append("&key="+"AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());


        return googlePlaceUrl.toString();
       //return "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+nearbyPlace+"&location="+latitude+","+longitude+"&radius=100&key=AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo";

        //return googlePlaceUrl.toString();
        //return "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+nearbyPlace+"&location="+latitude+","+longitude+"&radius=100&key=AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo";

    }
    private String getPlaceDetailsUrl(String placeID) // Use GooglePlaces API functions to find URL
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        googlePlaceUrl.append(placeID);
        googlePlaceUrl.append("&key="+"AIzaSyC142-1F7kvtpWFtCM3bXK6vfoq7xSPaqo");

        Log.d("getPlaceDetailsURl", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        //mViewPager.setCurrentItem(HOME_FRAGMENT);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public double getMyLatitude(){
        return latitude;
    }

    public double getMyLongitude(){
        return longitude;
    }
}