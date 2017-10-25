package com.example.luisafarias.myapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.luisafarias.myapplication.MainActivity;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.adapters.FeedListRecyclerViewAdapter;
import com.example.luisafarias.myapplication.model.Feed;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import java.util.ArrayList;
import java.util.List;

public class FeedListFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			_token = getArguments().getString("tokenKey");
			_feed = getArguments().getParcelable("feed");
			if (_feed != null && _token != null) {
				Log.d(FeedListFragment.class.getName(), _feed.getTitle());
			}
			_authorization = new TokenAuthorization(_token);
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		List<Feed> feedList = new ArrayList<Feed>();

		_view = inflater.inflate(R.layout.fragment_feed_list, container, false);
		_recycleView = _view.findViewById(R.id.recyclerView);
		_recycleViewAdapter =
			new FeedListRecyclerViewAdapter(_view.getContext(), feedList,
				_token);
		LinearLayoutManager lm = new LinearLayoutManager(getActivity());
		_recycleView.setLayoutManager(lm);
		_recycleView.setAdapter(_recycleViewAdapter);
		reloadFeeds();
		return _view;
	}

	public void reloadFeeds() {
		Repositorio.getInstance()
			.feedListAll(_authorization, new Repositorio.CallbackFeeds() {
				@Override
				public void onSuccess(List<Feed> feedList) {
					_recycleViewAdapter.updateAnswers(feedList);
				}

				@Override
				public void onFailure(Exception e) {
					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
	}

	private Authorization _authorization;
	private Feed _feed;
	private FeedListRecyclerViewAdapter _recycleViewAdapter;
	private RecyclerView _recycleView;
	private String _token;
	private View _view;
}
