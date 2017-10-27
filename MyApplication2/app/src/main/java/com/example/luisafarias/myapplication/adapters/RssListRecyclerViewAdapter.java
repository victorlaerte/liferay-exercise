package com.example.luisafarias.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luisafarias.myapplication.ItemListActivity;
import com.example.luisafarias.myapplication.MainActivity;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;
import com.example.luisafarias.myapplication.interfaces.ItemClickListener;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.util.Constants;

import java.util.List;

/**
 * Created by luisafarias on 16/10/17.
 */

public class RssListRecyclerViewAdapter
	extends RecyclerView.Adapter<RssListRecyclerViewAdapter.CustomViewHolder> {

	private List<Rss> _rssList;
	private Context _context;
	private String _token;

	public RssListRecyclerViewAdapter(Context context, List<Rss> rssList,
                                      String token) {
		this._context = context;
		set_feedList(rssList);
		set_token(token);
	}

	public void set_feedList(List<Rss> feedList) {
		this._rssList = feedList;
	}

	public void set_token(String token) {
		this._token = token;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(_context)
			.inflate(R.layout.rss_body, null);//dar uma olhada aqui depois
		CustomViewHolder viewHolder = new CustomViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(CustomViewHolder holder, int position) {
		final Rss rss = _rssList.get(position);
		holder.name.setText(rss.getChannel().getTitle());
		holder.id.setText(rss.getId());



		holder.setItemClickListener(new ItemClickListener() {
			@Override
			public void onClick(View view, int position, boolean isLongClick) {
				if (isLongClick) {
					Bundle bundle = new Bundle();
					bundle.putParcelable(Constants.RSS, rss);
					bundle.putString(Constants.TOKEN, _token);
					PopUpFragment popUpFragment = new PopUpFragment();
					popUpFragment.setArguments(bundle);
					popUpFragment.show(
						((FragmentActivity) _context).getSupportFragmentManager(),
						"idPopupFragment");

					Log.d("click longo", _rssList.get(position).getUrl());
				} else {
					Log.d("click curto", _rssList.get(position).getUrl());
					Intent intent = new Intent(_context, ItemListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putParcelable(Constants.RSS,rss);
					intent.putExtra(Constants.RSS,bundle);
					_context.startActivity(intent);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return _rssList.size();
	}

	public void updateAnswers(List<Rss> rsss) {
		this._rssList = rsss;
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
