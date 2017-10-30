package com.example.luisafarias.myapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.luisafarias.myapplication.activities.MainActivity;
import com.example.luisafarias.myapplication.R;
import com.example.luisafarias.myapplication.adapters.RssListRecyclerViewAdapter;
import com.example.luisafarias.myapplication.model.Rss;
import com.example.luisafarias.myapplication.model.Repositorio;
import com.example.luisafarias.myapplication.util.Constants;
import com.wedeploy.android.auth.Authorization;
import com.wedeploy.android.auth.TokenAuthorization;
import java.util.ArrayList;
import java.util.List;

public class RssListFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			_token = getArguments().getString(Constants.TOKEN_KEY);
			_rss = getArguments().getParcelable(Constants.RSS);
			if (_rss != null && _token != null) {
				Log.d(RssListFragment.class.getName(), _rss.getChannel().getTitle());
			}
			_authorization = new TokenAuthorization(_token);
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		List<Rss> feedList = new ArrayList<Rss>();

		_view = inflater.inflate(R.layout.fragment_rss_list, container, false);
		_recycleView = _view.findViewById(R.id.recyclerView);
		_recycleViewAdapter =
			new RssListRecyclerViewAdapter(_view.getContext(), feedList,
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
				public void onSuccess(List<Rss> feedList) {
					_recycleViewAdapter.updateAnswers(feedList);
				}

				@Override
				public void onFailure(Exception e) {
					Log.e(MainActivity.class.getName(), e.getMessage());
				}
			});
	}

	private Authorization _authorization;
	private Rss _rss;
	private RssListRecyclerViewAdapter _recycleViewAdapter;
	private RecyclerView _recycleView;
	private String _token;
	private View _view;
}
