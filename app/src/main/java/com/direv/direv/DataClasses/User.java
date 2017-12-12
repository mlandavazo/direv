package com.direv.direv.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * credit for class goes to: https://github.com/mitchtabian/Android-Instagram-Clone
 */

/**
 * may have to alt+insert getters and setters and tostring if there are errors
 */
public class User implements Parcelable {

    private String firstname;
    private String lastname;
    private String user_id;
    private String email;
    private String username;

    public User(String firstname, String lastname, String user_id, String email, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.user_id = user_id;
        this.email = email;
        this.username = username;
    }

    public User() {

    }


    protected User(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        user_id = in.readString();
        email = in.readString();
        username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(user_id);
        dest.writeString(email);
        dest.writeString(username);
    }
}
