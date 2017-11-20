package com.direv.direv.DataClasses;

import com.direv.direv.DataParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Malachi on 11/19/2017.
 */

public class Restaurant extends Location{
    double hours;
    Long phones;

    String address;
    String name;
    double foodRating,serviceRating,cleanRating;
    List<Photo> Reviews;
    private Location location;
public Restaurant(){

}
public Restaurant(JSONObject googlePlaceJson) {
  //  HashMap<String, String> googlePlaceMap = new HashMap<>();
    String placeName = "--NA--";
    String vicinity = "--NA--";
    //String reference = "";

    try {
        if (!googlePlaceJson.isNull("name")) {
            placeName = googlePlaceJson.getString("name");
        }
        if (!googlePlaceJson.isNull("vicinity")) {
            vicinity = googlePlaceJson.getString("vicinity");
        }


        this.address = vicinity;
        this.name = placeName;

    } catch (JSONException e) {
        e.printStackTrace();
    }
}


    public String getAddress() { return address;  }

    public void setAddress(String add) {this.address = add; }
    public String getName() { return name;  }

    public void setName(String name) {this.name = name; }
}

