package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.activities.NewsActivity;
import com.example.luisafarias.myapplication.interfaces.ItemClickListener;
import com.example.luisafarias.myapplication.model.Item;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

	public ItemAdapter(Context context, List<Item> feed) {
		this._feedItems = feed;
		this._context = context;
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_body, parent, false);
		return new ItemHolder(view);
		//        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		//        final View v = layoutInflater.inflate(R.layout.item_body,parent,false);
		//        return new ItemHolder(v);
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

		holder.setItemClickListener(new ItemClickListener() {
			@Override
			public void onClick(View view, int position, boolean isLongClick) {
				Intent intent = new Intent(_context, NewsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.LINK,item.getLink());
				intent.putExtra(Constants.LINK,bundle);
				_context.startActivity(intent);
			}
		});
	}

	public class ItemHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
		protected TextView titleTextField;
		protected TextView descriptionTextField;
		private ItemClickListener itemClickListener;

		private ImageView imageView;
		private TextView publicationDateTextField;

		public ItemHolder(View itemView) {
			super(itemView);
			titleTextField = itemView.findViewById(R.id.title);
			imageView = itemView.findViewById(R.id.image);
			descriptionTextField = itemView.findViewById(R.id.description);
			publicationDateTextField = itemView.findViewById(R.id.pubdate);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) { itemClickListener.onClick(v, getAdapterPosition(),false);

		}

		public void setItemClickListener(ItemClickListener itemClickListener){
			this.itemClickListener = itemClickListener;
		}
	}

	private List<Item> _feedItems;
	private Context _context;
	//private LayoutInflater _layoutInflater;
}
