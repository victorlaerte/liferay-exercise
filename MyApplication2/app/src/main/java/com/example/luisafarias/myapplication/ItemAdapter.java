package com.example.luisafarias.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisafarias.myapplication.model.FeedItem;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private static final String TAG="ItemAdapter";
    private List<FeedItem> feedITems;

    @Override
    public ItemAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView titleTextField;
        private TextView descriptionTextField;

        private ImageView imageView;
        private TextView publicationDateTextField;
        ItemHolder(View itemView) {
            super(itemView);
            titleTextField = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            descriptionTextField = (TextView) itemView.findViewById(R.id.description);
            publicationDateTextField = (TextView) itemView.findViewById(R.id.pubdate);
        }

    }
}
