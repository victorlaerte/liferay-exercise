package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.NewsActivity;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.util.AndroidUtil;
import com.example.luisafarias.myapplication.util.Constants;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> implements Filterable {

	public ItemAdapter(Context context, List<Item> feed) {
		this._feedItems = feed;
		this._context = context;
	}

	public void setFeedItems(List<Item> _feedItems) {
		this._feedItems = _feedItems;
	}

	public void setFeedItemsAux(List<Item> feedItemsAux) {
		this._feedItemsAux = feedItemsAux;
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_body, parent, false);

		return new ItemHolder(view);
	}

	@Override
	public int getItemCount() {
		return _feedItems.size();
	}

	public void updateAnswers(List<Item> items) {
		this._feedItems = items;
		notifyDataSetChanged();
	}

	private Item getItem(int adapterPosition) {
		return _feedItems.get(adapterPosition);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {
		final Item item = _feedItems.get(position);
		holder.titleTextField.setText(item.getTitle());
		if (AndroidUtil.isOnline(_context)){
			String urlImage = item.getUrlImage();
			ImageView imageView = holder.imageView;

			Glide.with(holder.view.getContext()).load(urlImage).into(imageView);

		}

		holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(_context, NewsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.LINK, item.getLink());
            intent.putExtra(Constants.LINK, bundle);
            _context.startActivity(intent);
        });
	}

	@Override
	public Filter getFilter() {
		if (_filter == null) {
			_filter = new Filter() {
				@Override
				protected FilterResults performFiltering(
						CharSequence constraint) {

					FilterResults filterResults = new FilterResults();

					if (constraint == null || constraint.length() == 0) {
						filterResults.values = _feedItemsAux;
						filterResults.count = _feedItemsAux.size();
					} else {

						List<Item> feedItemsAux = new ArrayList<>();

						for (Item item : _feedItemsAux) {
							if (removeAccent(item.getTitle().toUpperCase()).
									contains(removeAccent(constraint.toString().toUpperCase()))) {
								feedItemsAux.add(item);
							}
						}

						filterResults.values = feedItemsAux;
						filterResults.count = feedItemsAux.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint,
											  FilterResults results) {

					updateAnswers((List<Item>) results.values);
				}
			};
		}

		return _filter;
	}

	public class ItemHolder extends RecyclerView.ViewHolder {
		protected TextView titleTextField;

		public ImageView imageView;
		protected View view;

		public ItemHolder(View itemView) {
			super(itemView);
			view = itemView;
			titleTextField = itemView.findViewById(R.id.titleItem);
			imageView = itemView.findViewById(R.id.imageItem);
		}
	}

	private String removeAccent(String str){
		if (str != null){
			str = Normalizer.normalize(str, Normalizer.Form.NFD);
			str = str.replaceAll("[^\\p{ASCII}]", "");
		}
		return str;
	}

	private List<Item> _feedItems;
	private List<Item> _feedItemsAux;
	private Filter _filter;
	private Context _context;
}
