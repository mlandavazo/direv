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
    double foodRating,serviceRating,cleanRating;

    public Photo() {

    }

    public Photo(String caption, String date_created, String image_path, String photo_id,
                 String user_id, String tags, List<Like> likes, List<Comment> comments, double foodRating, double serviceRating, double cleanRating) {
        this.caption = caption;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.likes = likes;
        this.comments = comments;
        this.foodRating = foodRating;
        this.cleanRating = cleanRating;
        this.serviceRating = serviceRating;

    }

    protected Photo(Parcel in) {
        caption = in.readString();
        date_created = in.readString();
        image_path = in.readString();
        photo_id = in.readString();
        user_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caption);
        dest.writeString(date_created);
        dest.writeString(image_path);
        dest.writeString(photo_id);
        dest.writeString(user_id);
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

    public void setFoodRating(double foodRating){ this.foodRating=foodRating;}

    public double getFoodRating() { return foodRating;}

    public void setCleanRating(double cleanRating){ this.cleanRating=cleanRating;}

    public double getCleanRating() { return cleanRating;}

    public void setServiceRating(double serviceRating){ this.serviceRating=serviceRating;}

    public double getServiceRating() { return serviceRating;}

    @Override
    public String toString() {
        return "Photo{" +
                "caption='" + caption + '\'' +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", likes=" + likes +
                ", serviceRating=" + serviceRating +
                ", foodRating=" + foodRating +
                ",cleanRating=" + cleanRating +
                '}';
    }
}
