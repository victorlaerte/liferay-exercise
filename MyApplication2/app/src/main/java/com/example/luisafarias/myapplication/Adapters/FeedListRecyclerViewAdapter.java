package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;
import com.example.luisafarias.myapplication.interfaces.ItemClickListener;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.List;

/**
 * Created by luisafarias on 16/10/17.
 */

public class FeedListRecyclerViewAdapter
	extends RecyclerView.Adapter<FeedListRecyclerViewAdapter.CustomViewHolder> {

	private List<Feed> _feedList;
	private Context _context;
	private String _token;

	public FeedListRecyclerViewAdapter(Context context, List<Feed> feedList,
		String token) {
		this._context = context;
		set_feedList(feedList);
		set_token(token);
	}

	public void set_feedList(List<Feed> feedList) {
		this._feedList = feedList;
	}

	public void set_token(String token) {
		this._token = token;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(_context)
			.inflate(R.layout.feed_body, null);//dar uma olhada aqui depois
		CustomViewHolder viewHolder = new CustomViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(CustomViewHolder holder, int position) {
		final Feed feed = _feedList.get(position);
		holder.name.setText(feed.getTitle());
		holder.id.setText(feed.getId());

		holder.setItemClickListener(new ItemClickListener() {
			@Override
			public void onClick(View view, int position, boolean isLongClick) {
				if (isLongClick) {
					Bundle bundle = new Bundle();
					bundle.putParcelable(Constants.FEED, feed);
					bundle.putString(Constants.TOKEN, _token);
					PopUpFragment popUpFragment = new PopUpFragment();
					popUpFragment.setArguments(bundle);
					popUpFragment.show(
						((FragmentActivity) _context).getSupportFragmentManager(),
						"idPopupFragment");

					Log.d("click longo", _feedList.get(position).getTitle());
				} else {
					Log.d("click curto", _feedList.get(position).getTitle());
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return _feedList.size();
	}

	public void updateAnswers(List<Feed> feeds) {
		this._feedList = feeds;
		notifyDataSetChanged();
	}

	class CustomViewHolder extends RecyclerView.ViewHolder
		implements View.OnClickListener, View.OnLongClickListener {
		private ItemClickListener itemClickListener;
		protected TextView name;
		protected TextView id;

		public CustomViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
			this.name = view.findViewById(R.id.nome_url_recebida);
			this.id = view.findViewById(R.id.idUrlTest);
		}

		@Override
		public void onClick(View v) {
			itemClickListener.onClick(v, getAdapterPosition(), false);
		}

		@Override
		public boolean onLongClick(View v) {
			itemClickListener.onClick(v, getAdapterPosition(), true);
			return true;
		}

		public void setItemClickListener(ItemClickListener itemClickListener) {

			this.itemClickListener = itemClickListener;
		}
	}
}
