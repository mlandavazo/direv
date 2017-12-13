package com.direv.direv.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * credit for class goes to: https://github.com/mitchtabian/Android-Instagram-Clone
 */

public class Photo implements Parcelable {

    private String caption;
    private String date_created;
    private String image_path;
    private String photo_id;
    private String user_id;
    private List<Like> likes;
    private List<Comment> comments;
    double food_rating,service_rating,clean_rating;
    private String restaurant_name;
    private String restaurant_key;

    public Photo() {

    }

    public Photo(String caption, String date_created, String image_path, String photo_id,
                 String user_id, String tags, List<Like> likes, List<Comment> comments,
                 double foodRating, double serviceRating, double cleanRating,
                 String restaurantName, String restaurant_key) {
        this.caption = caption;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.likes = likes;
        this.comments = comments;
        this.food_rating = foodRating;
        this.clean_rating = cleanRating;
        this.service_rating = serviceRating;
        this.restaurant_name = restaurantName;
        this.restaurant_key = restaurant_key;
    }

    protected Photo(Parcel in) {
        caption = in.readString();
        date_created = in.readString();
        image_path = in.readString();
        photo_id = in.readString();
        user_id = in.readString();
        food_rating = in.readDouble();
        service_rating = in.readDouble();
        clean_rating = in.readDouble();
        restaurant_name = in.readString();
        restaurant_key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caption);
        dest.writeString(date_created);
        dest.writeString(image_path);
        dest.writeString(photo_id);
        dest.writeString(user_id);
        dest.writeDouble(food_rating);
        dest.writeDouble(clean_rating);
        dest.writeDouble(service_rating);
        dest.writeString(restaurant_name);
        dest.writeString(restaurant_key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Creator<Photo> getCREATOR() {
        return CREATOR;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }


    public double getFood_rating() {
        return food_rating;
    }

    public void setFood_rating(double food_rating) {
        this.food_rating = food_rating;
    }

    public double getService_rating() {
        return service_rating;
    }

    public void setService_rating(double service_rating) {
        this.service_rating = service_rating;
    }

    public double getClean_rating() {
        return clean_rating;
    }

    public void setClean_rating(double clean_rating) {
        this.clean_rating = clean_rating;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_key() {
        return restaurant_key;
    }

    public void setRestaurant_key(String restaurant_key) {
        this.restaurant_key = restaurant_key;
    }

    public double getOverallRating() {
        double overall = (service_rating + clean_rating + food_rating) / 3;
        if((overall % 1) > 0 && (overall % 1) < 0.25){
            return (overall - (overall % 1));
        }
        else if ((overall % 1) > 0.25 && (overall % 1) < 0.5){
            return (overall + (0.5 - (overall % 1)));
        }
        else if ((overall % 1) > 0.5){
            return (overall + (1 - (overall % 1)));
        }
        else{
            return overall;
        }
    }

    @Override
    public String toString() {
        return "Photo{" +
                "caption='" + caption + '\'' +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                ", food_rating=" + food_rating +
                ", service_rating=" + service_rating +
                ", clean_rating=" + clean_rating +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_key='" + restaurant_key + '\'' +
                '}';
    }
}
