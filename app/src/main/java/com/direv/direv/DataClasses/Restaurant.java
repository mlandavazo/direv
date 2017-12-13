package com.direv.direv.DataClasses;

import com.direv.direv.DataParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
//=======
//>>>>>>> 7693056296c94ad410f00363a0552d87ebc8b1be
import java.util.List;

/**
 * Created by Malachi on 11/19/2017.
 */

public class Restaurant {
    double hours;
    String phone;
    Location location;
    List<Photo> reviews;
    String key;

    public Restaurant(){

    }

    public Restaurant(Location location) {
        this.location = location;
        this.hours = 0.0;
        this.phone = "555-5555";

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Photo> getReviews() {
        return reviews;
    }

    public void setReviews(List<Photo> reviews) {
        this.reviews = reviews;
    }



}


