package com.example.luisafarias.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.fragments.PopUpFragment;
import com.example.luisafarias.myapplication.model.Feed;
import com.wedeploy.android.auth.Authorization;
import java.util.List;

/**
 * Created by luisafarias on 26/09/17.
 */

public class FeedListAdapter extends BaseAdapter {

	public FeedListAdapter(Context context, Authorization authorization,
		List<Feed> feeds) {
		this._activity = (Activity) context;
		this._context = context;
		this._feed = feeds;
		this._inflater = LayoutInflater.from(_context);
		this._authorization = authorization;
	}

	public int getCount() {
		return _feed.size();
	}

	public Feed getItem(int position) {
		return _feed.get(position);
	}

	public long getItemId(int position) {
		return Long.parseLong(_feed.get(position).getId());
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final View view = _inflater.inflate(R.layout.feed_body, parent, false);
		final Feed feed = _feed.get(position);

		TextView nome = view.findViewById(R.id.nome_url_recebida);
		TextView urlTest = view.findViewById(R.id.idUrlTest);
		nome.setText(feed.getTitle());
		urlTest.setText(feed.getId());

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(FeedListAdapter.class.getName(), "click curto");
				//                NewFeedFragment fragmentNewFeed = new NewFeedFragment();
				//                FragmentManager fm = getFragmentManager(); //error
				//                FragmentTransaction ft = fm.beginTransaction();
				//                ft.add(R.id.fragment_new_url,fragmentNewFeed);
				//                ft.commit();

			}
		});

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putParcelable("feed", feed);
				bundle.putString("token", _authorization.getToken());
				PopUpFragment popUpFragment = new PopUpFragment();
				popUpFragment.setArguments(bundle);
				popUpFragment.show(
					((FragmentActivity) _context).getSupportFragmentManager(),
					"idPopupFragment");

				/**before with PopUpActivity***/
				//                Intent intent = new Intent(_context, PopUpActivity.class);
				//                intent.putExtra("feed",feed);
				//                intent.putExtra("token", _authorization.getToken());
				//
				//                Log.d(FeedListAdapter.class.getName(),"click long");
				//                Toast.makeText(_context,"long click",Toast.LENGTH_LONG).show();
				//                _context.startActivity(intent);
				/*********/
				//Repositorio.getInstance(_context).removeFeed(feed,_authorization);
				return true;
			}
		});

		return view;
	}

	private Activity _activity;
	private Authorization _authorization;
	private Context _context;
	private final List<Feed> _feed;
	private LayoutInflater _inflater;
}
