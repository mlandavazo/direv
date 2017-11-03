package com.direv.direv;

/**
 * Created by Malachi on 11/2/2017.
 */

public class FeedItem {
    private int profilepic = R.drawable.personicon;

    private int profilename = R.id.listprofilename;

    private int restaurantname = R.id.listrestaurantname;

    private int timeposted = R.id.listtimeposted;

    private int reviewpicture = R.drawable.bigmac;

    private int heart = R.drawable.heartoff;

    private int numlikes = R.id.listnumberoflikes;

    private int comment = R.drawable.listcomments;

    private int numcomments = R.id.listnumberofcomments;

    private int reviewbackground = R.drawable.listratingbackground;

    private int reviewscore = R.id.listitemrating;

    FeedItem(){

    }

    public int getProfileName(){

        return profilename;
    }
    public int getProfilePic (){

        return profilepic;
    }

    public int getRestaurantName(){

        return restaurantname;
    }

    public int getReviewPicture(){

        return reviewpicture;
    }

    public int getHeartPic(){

        return heart;
    }

    public int getCommentPic(){

        return comment;
    }

    public int getRatingBackground(){

        return reviewbackground;
    }

}
