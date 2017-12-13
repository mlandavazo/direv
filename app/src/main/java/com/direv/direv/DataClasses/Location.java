package com.direv.direv.DataClasses;

import com.direv.direv.DataParser;
import com.direv.direv.HomeActivity;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

//=======
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Malachi on 11/19/2017.
 */

public class Location {
    String lat,lng;
    String address;
    String id;
    String name;
    String place_id;

    public Location(){

    }

    public Location(String lat, String lng, String address, String id, String name, String place_id) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
    }

    public Location (JSONObject googlePlaceJson){
        // HashMap<String, String> googlePlaceMap = new HashMap<>();

        String latitude= "";
        String longitude="";
        String vicinity= "--NA--";
        String id = "";
        String name = "--NA--";
        String place_id= "";

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
            vicinity = googlePlaceJson.getString("vicinity");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            id = googlePlaceJson.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            name = googlePlaceJson.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            place_id = googlePlaceJson.getString("place_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.lat = latitude;
        this.lng = longitude;
        this.address = vicinity;
        this.id = id;
        this.name = name;
        this.place_id = place_id;

    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }


    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", place_id='" + place_id + '\'' +
                '}';
    }
}