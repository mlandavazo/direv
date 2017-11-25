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

    List<Photo> reviews;
    //Photo photo;
    private Location location;
public Restaurant(){

}
public Restaurant(JSONObject googlePlaceJson,Photo photo) {
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
        addPhoto(photo);

    } catch (JSONException e) {
        e.printStackTrace();
    }
}


    public String getAddress() { return address;  }

    public void setAddress(String add) {this.address = add; }
    public String getName() { return name;  }

    public void setName(String name) {this.name = name; }
    public List<Photo> getPhoto(){return reviews;}
    public void addPhoto(Photo photo){this.reviews.add(photo);}

    public double RatingAverage(List<Photo> reviews, String type){
        int reviewsize = reviews.size();
        double avgClean = 0;
        double avgFood = 0;
        double avgService = 0;
        if(type == "clean") {
            for (int i = 0; i < reviewsize; i++) {

                avgClean += reviews.get(i).cleanRating;
            }
            return avgClean/reviewsize;
        }
           else if(type =="food") {
                for (int i = 0; i < reviewsize; i++) {
                    avgFood += reviews.get(i).foodRating;
                }
                return avgFood/reviewsize;
            }
            else {
                for (int i = 0; i < reviewsize; i++) {
                    avgService += reviews.get(i).serviceRating;
                }
                return avgService/reviewsize;
            }

        }

    }


