package com.example.luisafarias.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisafarias.myapplication.model.FeedItem;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by luisafarias on 19/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private static final String TAG="ItemAdapter";
    private List<FeedItem> feedItems;

    ItemAdapter(List<FeedItem> feed){this.feedItems = feed;}

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.feed_item,parent,false);
        return new ItemHolder(v);
    }

    @Override
    public int getItemCount() {return feedItems.size();}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {

        FeedItem item = feedItems.get(position);

        holder.titleTextField.setText(item.get_title());
        Document doc = Jsoup.parse(item.get_description());
        Element imageElement = doc.select("img").first();
        if (imageElement != null){
            String absoluteUrl = imageElement.absUrl("src");
            if (absoluteUrl != null){

                holder.descriptionTextField.setText(doc.body().text());
            }
        }
        holder.publicationDateTextField.setText(item.get_publicationDate());
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