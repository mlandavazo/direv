package com.direv.direv.DataClasses;

import com.direv.direv.DataParser;
import com.direv.direv.HomeActivity;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Malachi on 11/19/2017.
 */

public class Location extends HomeActivity {
double lat,lng;
String address;
DataParser dataParser;
HomeActivity homeActivity;

public Location(){

}
public Location (JSONObject googlePlaceJson){
    HashMap<String, String> googlePlaceMap = new HashMap<>();
    String placeName = "--NA--";
    String vicinity= "--NA--";
    String latitude= "";
    String longitude="";
    String reference="";
    try {
        latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
    } catch (JSONException e) {
        e.printStackTrace();
    }
    try {
        longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
    } catch (JSONException e) {
        e.printStackTrace();
    }

    try {
        reference = googlePlaceJson.getString("reference");
    } catch (JSONException e) {
        e.printStackTrace();
    }
   this.address = vicinity;
    this.lat = Double.parseDouble(latitude);
    this.lng = Double.parseDouble(longitude);
}
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() { return address;  }

    public void setAddress(String add) {this.address = add; }
}
