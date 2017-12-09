package com.direv.direv.DataClasses;

import java.util.List;

/**
 * Created by Malachi on 11/19/2017.
 */

public class Restaurant {
    double hours;
    String phone;
    Location location;
    List<Photo> reviews;


    public Restaurant(){

    }

    public Restaurant(Location location) {
        this.location = location;
        this.hours = 0.0;
        this.phone = "555-5555";
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


