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

static class ViewHolder{
    ImageView profilePicture, reviewPicture, heart, comment, ratingBackground;
    TextView profileName, restaurantName, timePosted, numComments, numLikes, rating, reviewText;

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        final ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder = new ViewHolder();
            FeedItem currentFeedItem = getItem(position);
            holder.profileName = (TextView) listItemView.findViewById(R.id.listprofilename);
            holder.profilePicture = (ImageView) listItemView.findViewById(R.id.listprofilepic);
            holder.profilePicture.setImageResource(currentFeedItem.getProfilePic());
            holder.restaurantName= (TextView) listItemView.findViewById(R.id.listrestaurantname);
            holder.reviewPicture = (ImageView) listItemView.findViewById(R.id.listreviewpicture);
            holder.reviewPicture.setImageResource(currentFeedItem.getReviewPicture());
            holder.timePosted = (TextView) listItemView.findViewById(R.id.listtimeposted);
            holder.heart = (ImageView) listItemView.findViewById(R.id.listheart);
            holder.heart.setImageResource(currentFeedItem.getHeartPic());
            holder.comment = (ImageView) listItemView.findViewById(R.id.listcomments);
            holder.comment.setImageResource(currentFeedItem.getCommentPic());
            holder.ratingBackground = (ImageView) listItemView.findViewById(R.id.listratingbackground);
            holder.ratingBackground.setImageResource(currentFeedItem.getRatingBackground());
            holder.numComments= (TextView) listItemView.findViewById(R.id.listnumberofcomments);
            holder.numLikes= (TextView) listItemView.findViewById(R.id.listnumberoflikes);
            holder.rating= (TextView) listItemView.findViewById(R.id.listitemrating);
            holder.reviewText = (TextView) listItemView.findViewById(R.id.listitemcommenttext);

            holder.restaurantName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   // Intent intent = new Intent(context, RestaurantActivity.class);
                   // context.startActivity(intent);

                }
            });
        }


        return listItemView;
    }
}

