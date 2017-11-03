package com.direv.direv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Malachi on 11/2/2017.
 */

public class FeedItemAdapter extends ArrayAdapter<FeedItem> {
    public FeedItemAdapter(Context context, ArrayList<FeedItem> feedList) {
        super(context, 0, feedList);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        FeedItem currentFeedItem = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView profileName = (TextView) listItemView.findViewById(R.id.listprofilename);
        //bikeType.setText(currentRouteCard.getBikeText());

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView profilePicture = (ImageView) listItemView.findViewById(R.id.listprofilepic);
        // Check if an image is provided for this word or no
        profilePicture.setImageResource(currentFeedItem.getProfilePic());

        TextView restaurantName= (TextView) listItemView.findViewById(R.id.listrestaurantname);
        // difficulty.setText(currentRouteCard.getDifficultyText());

        ImageView reviewPicture = (ImageView) listItemView.findViewById(R.id.listreviewpicture);
        reviewPicture.setImageResource(currentFeedItem.getReviewPicture());

        TextView timePosted = (TextView) listItemView.findViewById(R.id.listtimeposted);
        //rating_text.setText(currentRouteCard.getRatingText());

        ImageView heart = (ImageView) listItemView.findViewById(R.id.listheart);
        heart.setImageResource(currentFeedItem.getHeartPic());

        ImageView comment = (ImageView) listItemView.findViewById(R.id.listcomments);
        comment.setImageResource(currentFeedItem.getCommentPic());

        ImageView ratingBackground = (ImageView) listItemView.findViewById(R.id.listratingbackground);
        ratingBackground.setImageResource(currentFeedItem.getRatingBackground());

        TextView numComments= (TextView) listItemView.findViewById(R.id.listnumberofcomments);

        TextView numLikes= (TextView) listItemView.findViewById(R.id.listnumberoflikes);

        TextView rating= (TextView) listItemView.findViewById(R.id.listitemrating);

        return listItemView;
    }
}

